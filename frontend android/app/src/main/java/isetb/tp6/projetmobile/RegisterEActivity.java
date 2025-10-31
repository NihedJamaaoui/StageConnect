package isetb.tp6.projetmobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.bumptech.glide.Glide;
import isetb.tp6.projetmobile.model.Utilisateur;
import isetb.tp6.projetmobile.utils.Apis;
import isetb.tp6.projetmobile.utils.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEActivity extends AppCompatActivity {

    EditText nameEditText, emailEditText, passwordEditText, addressEditText, mobileEditText;
    Button registerButton;
    TextView loginTextView;
    ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // Store the selected image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registere);

        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        addressEditText = findViewById(R.id.editTextAdresse);
        mobileEditText = findViewById(R.id.editTextMobile);
        imageView = findViewById(R.id.imageView);
        registerButton = findViewById(R.id.cirRegButton);
        loginTextView = findViewById(R.id.tv_login);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterEActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            selectedImageUri = data.getData();

            // Load the selected image into the ImageView using Glide
            Glide.with(this).load(selectedImageUri).into(imageView);
        }
    }

    private void performRegistration() {
        String nom = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String adresse = addressEditText.getText().toString().trim();
        String telephone = mobileEditText.getText().toString().trim();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the selected image URI to a File object
        File imageFile = convertImageToFile(selectedImageUri);

        // Create a MultipartBody.Part from the File
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        Service service = new Apis().getService();
        RequestBody nomRequestBody = RequestBody.create(MediaType.parse("text/plain"), nom);
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody adresseRequestBody = RequestBody.create(MediaType.parse("text/plain"), adresse);
        RequestBody telephoneRequestBody = RequestBody.create(MediaType.parse("text/plain"), telephone);

        Call<Utilisateur> call = service.registerEntre(emailRequestBody, passwordRequestBody, nomRequestBody, adresseRequestBody, telephoneRequestBody, imagePart);


        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Utilisateur utilisateur = response.body();
                    if (utilisateur != null) {
                        Toast.makeText(RegisterEActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterEActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterEActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterEActivity.this, "Failed to register. Status code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Log.e("RegisterActivity", "Error during registration: " + t.getMessage());
                Toast.makeText(RegisterEActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to convert an image URI to a File object
    private File convertImageToFile(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            byte[] bytes = getBytes(inputStream);

            // Save the bytes to a temporary file
            File tempFile = File.createTempFile("tempImage", null, getCacheDir());
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bytes);
            fos.close();

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert an InputStream to a byte array
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}