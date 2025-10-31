package isetb.tp6.projetmobile.model;

import java.io.Serializable;
import java.util.List;

public class Stagiaire implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String date_naissance;
    private String cv;

    private List<Utilisateur> utilisateurs;

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Stagiaire() {
    }

    public Stagiaire(Long id, String nom, String prenom, String date_naissance, String cv) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.cv = cv;
    }

    public Stagiaire(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public String getCv() {
        return cv;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}