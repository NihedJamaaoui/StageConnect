package isetb.tp6.projetmobile.model;

public class Postuler {
    private Long id;
    private String cv;
    private boolean decision;
    private Stagiaire stagiaire;
    private Offre offre;

    public Postuler(Long id, String cv, boolean decision, Stagiaire stagiaire, Offre offre) {
        this.id = id;
        this.cv = cv;
        this.decision = decision;
        this.stagiaire = stagiaire;
        this.offre = offre;
    }

    public Postuler(String cv, boolean decision, Stagiaire stagiaire, Offre offre) {
        this.cv = cv;
        this.decision = decision;
        this.stagiaire = stagiaire;
        this.offre = offre;
    }

    public Long getId() {
        return id;
    }

    public String getCv() {
        return cv;
    }

    public boolean isDecision() {
        return decision;
    }

    public Stagiaire getStagiaire() {
        return stagiaire;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public void setStagiaire(Stagiaire stagiaire) {
        this.stagiaire = stagiaire;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }
}
