package isetb.tp6.projetmobile.model;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Offre  implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String Date;
    private String adr;
    private Utilisateur utilisateur;
    private List<Postuler> postuler;
    private Stagiaire stagiaire; // Assuming this is a field in your Offre class


    public Offre(String title, String description, String adr) {
        this.title = title;
        this.description = description;
        this.adr = adr;
    }

    public Offre(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Postuler> getPostuler() {
        return postuler;
    }

    public void setPostuler(List<Postuler> postuler) {
        this.postuler = postuler;
    }
    private void writeObject(java.io.ObjectOutputStream out) throws IOException, IOException {
        out.defaultWriteObject();
        // You can skip writing the Stagiaire object if it's not needed during serialization
        // out.writeObject(stagiaire);
    }

    // Add this method if Stagiaire is not needed during deserialization
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // You can skip reading the Stagiaire object if it's not needed during deserialization
        // stagiaire = (Stagiaire) in.readObject();
    }
}
