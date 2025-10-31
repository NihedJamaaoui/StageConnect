package isetb.tp6.projetmobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp6.projetmobile.adapter.OffreAdapter;
import isetb.tp6.projetmobile.model.Offre;
import isetb.tp6.projetmobile.model.Stagiaire;
import isetb.tp6.projetmobile.utils.Apis;
import isetb.tp6.projetmobile.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOffre extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText adrEditText;
    private Button createButton;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offre);

        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        adrEditText = findViewById(R.id.editTextAdr);
        createButton = findViewById(R.id.btnCreate);

        prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOffre();
            }
        });
    }

    private void createOffre() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String adr = adrEditText.getText().toString();
        Long userId = getUserIdFromLocalStorage();

        Offre newOffre = new Offre(title, description, adr);

        Service service = new Apis().getService();
        Call<Offre> call = service.createOffre(String.valueOf(userId), newOffre);
        call.enqueue(new Callback<Offre>() {
            @Override
            public void onResponse(Call<Offre> call, Response<Offre> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateOffre.this, "Offre created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateOffre.this, "Failed to create Offre", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Offre> call, Throwable t) {
                Toast.makeText(CreateOffre.this, "Failed to create Offre", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Long getUserIdFromLocalStorage() {
        return prefs.getLong("userId", -1);
    }
}
