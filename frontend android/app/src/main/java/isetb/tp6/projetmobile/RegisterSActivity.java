package isetb.tp6.projetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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

public class RegisterSActivity extends AppCompatActivity {

    EditText nameEditText, emailEditText, passwordEditText, addressEditText;
    Button registerButton,btnPickCV;
    TextView loginTextView;
    ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_PDF_REQUEST = 2;

    private Uri selectedImageUri;
    private Uri selectedPdfUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgisters);
        nameEditText = findViewById(R.id.editTextnoms);
        nameEditText = findViewById(R.id.editTextprenoms);
        emailEditText = findViewById(R.id.editTextEmails);
        passwordEditText = findViewById(R.id.editTextPasswords);
        addressEditText = findViewById(R.id.editTextdate_naissance);
        btnPickCV  = findViewById(R.id.btncv);
        imageView = findViewById(R.id.imgvs);
        registerButton = findViewById(R.id.cirRegButton);
        loginTextView = findViewById(R.id.tv_login);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        btnPickCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCV();
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
                startActivity(new Intent(RegisterSActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void pickCV() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_REQUEST);
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
            // Handle image pick
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(imageView);
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Handle PDF pick
            selectedPdfUri = data.getData();
            // You can add additional handling or display the PDF file name if needed
            Toast.makeText(this, "Selected PDF File: " + getFileName(selectedPdfUri), Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex >= 0) {
                        result = cursor.getString(columnIndex);
                    } else {
                        // Handle the case where DISPLAY_NAME column is not found
                        Log.e("RegisterActivity", "DISPLAY_NAME column not found in cursor");
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }



    private void performRegistration() {
        String nom = nameEditText.getText().toString().trim();
        String prenom = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String date_naissance = addressEditText.getText().toString().trim();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || prenom.isEmpty() || date_naissance.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedPdfUri == null) {
            Toast.makeText(this, "Please select a PDF file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the selected image URI to a File object
        File imageFile = convertImageToFile(selectedImageUri);

        // Create a MultipartBody.Part from the File
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);


        File pdfFile = convertPdfToFile(selectedPdfUri);
        RequestBody requestPdf = RequestBody.create(MediaType.parse("application/pdf"), pdfFile);
        MultipartBody.Part pdfPart = MultipartBody.Part.createFormData("cv", pdfFile.getName(), requestPdf);



        Service service = new Apis().getService();
        RequestBody nomRequestBody = RequestBody.create(MediaType.parse("text/plain"), nom);
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody prenomRequestBody = RequestBody.create(MediaType.parse("text/plain"), prenom);
        RequestBody dateNaissanceRequestBody = RequestBody.create(MediaType.parse("text/plain"), date_naissance);

        Call<Utilisateur> call = service.registerStag(emailRequestBody, passwordRequestBody, nomRequestBody, prenomRequestBody, dateNaissanceRequestBody, imagePart, pdfPart);


        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Utilisateur utilisateur = response.body();
                    if (utilisateur != null) {
                        Toast.makeText(RegisterSActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterSActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterSActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterSActivity.this, "Failed to register. Status code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Log.e("RegisterActivity", "Error during registration: " + t.getMessage());
                Toast.makeText(RegisterSActivity.this, "Failed to register: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private File convertPdfToFile(Uri pdfUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            byte[] bytes = getBytes(inputStream);

            File pdfFile = File.createTempFile("tempCV", ".pdf", getCacheDir());
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(bytes);
            fos.close();

            return pdfFile;
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