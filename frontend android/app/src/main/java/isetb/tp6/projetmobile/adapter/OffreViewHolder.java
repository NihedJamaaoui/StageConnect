package isetb.tp6.projetmobile.adapter;

import static android.content.Context.MODE_PRIVATE;
import static isetb.tp6.projetmobile.LoginActivity.PREFS_NAME;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isetb.tp6.projetmobile.R;
import isetb.tp6.projetmobile.model.Offre;
import isetb.tp6.projetmobile.model.Postuler;
import isetb.tp6.projetmobile.model.PostulerDatabase;
import isetb.tp6.projetmobile.model.Stagiaire;

public class OffreViewHolder extends RecyclerView.ViewHolder {
    TextView t1, t2, t3, t4;
    ImageView postuler;
    public Button updateButton;
    public Button deleteButton;
    public Button createButton;

    private SharedPreferences prefs;
    PostulerDatabase db;
    private OffreAdapter.OnItemClickListener onItemClickListener;
    private List<Offre> list;

    public OffreViewHolder(@NonNull View itemView, List<Offre> list) {
        super(itemView);
        t1 = itemView.findViewById(R.id.offerTitle);
        t2 = itemView.findViewById(R.id.offerDescription);
        t3 = itemView.findViewById(R.id.Date);
        t4 = itemView.findViewById(R.id.adr);
        updateButton = itemView.findViewById(R.id.update);
        deleteButton = itemView.findViewById(R.id.delete);
        createButton = itemView.findViewById(R.id.create);
        postuler = itemView.findViewById(R.id.ps);
        prefs = itemView.getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        this.list = list; // Initialize the list


        if (createButton != null) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onCreateClick();
                    }
                }
            });
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUpdateClick(getAdapterPosition());
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteClick(getAdapterPosition());
                }
            }
        });

        postuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your existing logic for postuler
            }
        });
    }

    public void bind(Offre offre) {
        t1.setText(offre.getTitle());
        t2.setText(offre.getDescription());
        t3.setText(offre.getDate());
        t4.setText(offre.getAdr());

        db = new PostulerDatabase(itemView.getContext());
        postuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db != null) {
                    Stagiaire loggedStagiaire = getStagiareIdFromLocalStorage();
                    Offre selectedOffre = offre;
                    Log.d("itemView.getContext()", "Clicked on Offre with ID: " + selectedOffre.getId());
                    Offre offid = new Offre(selectedOffre.getId());
                    boolean x = db.addPostuler(new Postuler(getCvFromLocalStorage(), false, loggedStagiaire, offid));
                    if (x) {
                        Toast.makeText(itemView.getContext(), "is success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(itemView.getContext(), "is failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("OffreViewHolder", "PostulerDatabase is null");
                }
            }
        });
    }

    private Stagiaire getStagiareIdFromLocalStorage() {
        Long userId = getUserIdFromLocalStorage();
        return new Stagiaire(userId);
    }

    private Long getUserIdFromLocalStorage() {
        return prefs.getLong("userId", -1);
    }

    private String getCvFromLocalStorage() {
        return prefs.getString("cv", "");
    }
}
