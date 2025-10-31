package projet.StageConnect.services;

import java.util.List;
import java.util.Map;

import projet.StageConnect.entites.Offre;
import projet.StageConnect.entites.Stagiaire;
import projet.StageConnect.entites.Utilisateur;

public interface OffreService {
	
	public Offre createOffre(Offre off,Long id);
	public Offre updateOffre(Long id, Offre off);
	public List<Offre> getAllOffres() ;
       
    
	void deleteOffre(Long id);
	
    List<Offre> getOffersByUserId(Long userId);
    //List<Offre> getAllStagiaireOffres();
    List<Offre> getAllEntrepriseOffres();

    public Long getEntrepriseIdByOffreId(Long offreId);
}