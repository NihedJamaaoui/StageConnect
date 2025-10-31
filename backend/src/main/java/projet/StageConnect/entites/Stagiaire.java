package projet.StageConnect.entites;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

	
	@Entity
	@Table(name = "stagiaire_utilisateur")
  
	public class Stagiaire {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;
	    private String prenom;
	    private String date_naissance;
	    private String cv;
	    
	    public String getCv() {
			return cv;
		}
		public void setCv(String cv) {
			this.cv = cv;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getPrenom() {
			return prenom;
		}
		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}
		public String getDate_naissance() {
			return date_naissance;
		}
		public void setDate_naissance(String date) {
			this.date_naissance = date;
		}
	
	}


