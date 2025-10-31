package isetb.tp6.projetmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp6.projetmobile.model.Offre;
import isetb.tp6.projetmobile.utils.Apis;
import isetb.tp6.projetmobile.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOffreActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText AdrEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_offre);

        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        AdrEditText = findViewById(R.id.editTextAdr);

        updateButton = findViewById(R.id.btnUpdate);


        Intent intent = getIntent();
        Offre selectedOffre = (Offre) intent.getSerializableExtra("selectedOffre");


        titleEditText.setText(selectedOffre.getTitle());
        descriptionEditText.setText(selectedOffre.getDescription());
        AdrEditText.setText(selectedOffre.getAdr());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOffre(selectedOffre);
            }
        });
    }

    private void updateOffre(Offre selectedOffre) {
        String updatedTitle = titleEditText.getText().toString();
        String updatedDescription = descriptionEditText.getText().toString();
        String updatedAdr = AdrEditText.getText().toString();

        selectedOffre.setTitle(updatedTitle);
        selectedOffre.setDescription(updatedDescription);
        selectedOffre.setAdr(updatedAdr);

        Service service = new Apis().getService();
        Call<Offre> call = service.updateOffre(selectedOffre.getId(), selectedOffre);
        call.enqueue(new Callback<Offre>() {
            @Override
            public void onResponse(Call<Offre> call, Response<Offre> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateOffreActivity.this, "Offre updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateOffreActivity.this, "Failed to update Offre", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Offre> call, Throwable t) {
                Toast.makeText(UpdateOffreActivity.this, "Failed to update Offre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
