package isetb.tp6.projetmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import isetb.tp6.projetmobile.model.EmailAndPasswordRequest;
import isetb.tp6.projetmobile.model.Entreprise;
import isetb.tp6.projetmobile.model.LoginResponse;
import isetb.tp6.projetmobile.model.Stagiaire;
import isetb.tp6.projetmobile.model.Utilisateur;
import isetb.tp6.projetmobile.utils.Apis;
import isetb.tp6.projetmobile.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView textView;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        textView = findViewById(R.id.tv_regiter);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrationChoiceDialog();
            }
        });
    }

    private void showRegistrationChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Registration Type")
                .setMessage("Select the type of registration:")
                .setPositiveButton("Entreprise", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Employee
                        startActivity(new Intent(LoginActivity.this, RegisterEActivity.class));
                    }
                })
                .setNegativeButton("Stagiaire", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Student
                        startActivity(new Intent(LoginActivity.this, RegisterSActivity.class));
                    }
                })
                .show();
    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        Service service = new Apis().getService();
        Call<LoginResponse> call = service.loginUser(new EmailAndPasswordRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginActivity", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.getUser() != null && !loginResponse.getUser().isEmpty()) {

                        Utilisateur utilisateur = loginResponse.getUser().get(0);
                        Stagiaire stagiaire = utilisateur.getStagiaire();

                        if (stagiaire != null) { // Check if stagiaire is not null
                            String cv = stagiaire.getCv();
                            storeUserInfoInLocalStorage(utilisateur.getId(), cv);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, EntrepriseActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login. Status code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Error during login: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeUserInfoInLocalStorage(Long userId, String cv) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong("userId", userId);
        editor.putString("cv", cv);
        editor.apply();
    }
}
