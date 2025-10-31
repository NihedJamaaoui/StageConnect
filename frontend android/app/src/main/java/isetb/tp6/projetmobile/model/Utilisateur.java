package isetb.tp6.projetmobile.model;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private Long id;
    private String image;
    private String email;
    private String password;
    private Entreprise entreprise;
    private Stagiaire stagiaire;
    public Utilisateur() {
    }

    public Utilisateur(Long id, String image, String email, String password, Entreprise entreprise, Stagiaire stagiaire) {
        this.id = id;
        this.image = image;
        this.email = email;
        this.password = password;
        this.entreprise = entreprise;
        this.stagiaire = stagiaire;
    }

    public Long getId() {
        return id;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Stagiaire getStagiaire() {
        return stagiaire;
    }

    public void setStagiaire(Stagiaire stagiaire) {
        this.stagiaire = stagiaire;
    }
}

