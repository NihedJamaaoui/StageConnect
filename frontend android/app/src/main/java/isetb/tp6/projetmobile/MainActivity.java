package isetb.tp6.projetmobile;

import static isetb.tp6.projetmobile.LoginActivity.PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isetb.tp6.projetmobile.adapter.OffreAdapter;
import isetb.tp6.projetmobile.model.Offre;
import isetb.tp6.projetmobile.model.Postuler;
import isetb.tp6.projetmobile.model.PostulerDatabase;
import isetb.tp6.projetmobile.utils.Apis;
import isetb.tp6.projetmobile.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    MainActivity extends AppCompatActivity implements OffreAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private OffreAdapter offreAdapter;
    Button createButton;

    private SharedPreferences sharedPreferences;

    PostulerDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("favo", MODE_PRIVATE);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        offreAdapter = new OffreAdapter(null, this);

        recyclerView.setAdapter(offreAdapter);
        createButton = findViewById(R.id.create);


        initializeData();
        //affichepostulationbyid();
        //afficheallpostulation();
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateOffreActivity();
            }
        });
    }

    @Override
    public void onCreateClick() {
        openCreateOffreActivity();
    }

    private void openCreateOffreActivity() {
        Intent intent = new Intent(MainActivity.this, CreateOffre.class);
        startActivity(intent);
    }

    private void initializeData() {
        Service service = new Apis().getService();
        Call<List<Offre>> call = service.getAllOffers();
        call.enqueue(new Callback<List<Offre>>() {
            @Override
            public void onResponse(Call<List<Offre>> call, Response<List<Offre>> response) {
                if (response.isSuccessful()) {
                    List<Offre> offreList = response.body();
                    Log.d("MainActivity", "Received data: " + offreList);
                    offreAdapter.setData(offreList);
                }
            }

            @Override
            public void onFailure(Call<List<Offre>> call, Throwable t) {
                Log.e("MainActivity", "Error fetching data: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Offre selectedOffre) {
        boolean isFavorite = exist_favo(Math.toIntExact(selectedOffre.getId()));
        boolean newFavoriteStatus = !isFavorite;
        add_favo(Math.toIntExact(selectedOffre.getId()), newFavoriteStatus);

        ImageView favoriteIcon = findViewById(R.id.favoriteIcon);
        if (newFavoriteStatus) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite);
        }

        String toastMessage = newFavoriteStatus ? "Added to favorites" : "Removed from favorites";
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

        if (!newFavoriteStatus) {
            remove_Favo(Math.toIntExact(selectedOffre.getId()));
        }
    }

    private boolean exist_favo(int offerId) {
        return sharedPreferences.getBoolean(String.valueOf(offerId), false);
    }

    private void add_favo(int offerId, boolean isFavorite) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(String.valueOf(offerId), isFavorite);
        editor.apply();
    }

    private void remove_Favo(int offerId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(offerId));
        editor.apply();
    }
    @Override
    public void onUpdateClick(int position) {
        Offre selectedOffre = offreAdapter.getItem(position);
        // Call the Update Activity with the selected Offre data
        Intent intent = new Intent(MainActivity.this, UpdateOffreActivity.class);
        intent.putExtra("selectedOffre", selectedOffre);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Offre selectedOffre = offreAdapter.getItem(position);
        // Call the method to delete the Offre from the database
        deleteOffre(selectedOffre.getId());
    }

    private void deleteOffre(long offreId) {
        Service service = new Apis().getService();
        Call<Void> call = service.deleteOffre(offreId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Offre deleted successfully", Toast.LENGTH_SHORT).show();
                    // Refresh the data after deletion
                    initializeData();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete Offre", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Error deleting Offre: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to delete Offre", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void affichepostulationbyid() {
        long stagiaireId = getUserIdFromLocalStorage();
        List<Postuler> postulers = db.getPostulerByStagiaireId(stagiaireId);

        for (Postuler postuler : postulers) {
            Log.d("MainActivity", "Postuler ID: " + postuler.getId());
            Log.d("MainActivity", "CV: " + postuler.getCv());
            Log.d("MainActivity", "Decision: " + postuler.isDecision());
            Log.d("MainActivity", "Stagiaire ID: " + postuler.getStagiaire().getId());
            Log.d("MainActivity", "Offre ID: " + postuler.getOffre().getId());
            Log.d("MainActivity", "----------------: " + postuler.getOffre().getId());
        }
    }

    private void afficheallpostulation() {
        long stagiaireId = getUserIdFromLocalStorage();
        List<Postuler> postulers = db.getAllPostulers();

        for (Postuler postuler : postulers) {
            Log.d("MainActivity", "Postuler ID: " + postuler.getId());
            Log.d("MainActivity", "CV: " + postuler.getCv());
            Log.d("MainActivity", "Decision: " + postuler.isDecision());
            Log.d("MainActivity", "Stagiaire ID: " + postuler.getStagiaire().getId());
            Log.d("MainActivity", "Offre ID: " + postuler.getOffre().getId());
        }
    }

    private Long getUserIdFromLocalStorage() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong("userId", -1);
    }
    private String getCvFromLocalStorage() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString("cv", "");
    }

}

