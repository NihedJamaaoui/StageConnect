package isetb.tp6.projetmobile.model;

import java.io.File;

public class RegisterRequest {
    private String email;
    private String password;
    private String nom;
    private String adresse;
    private String telephone;
    private File imageFile;
    public RegisterRequest(String email, String password, String nom, String adresse, String telephone, File image) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.imageFile = image;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
