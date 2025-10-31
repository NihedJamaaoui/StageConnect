package projet.StageConnect.services;

import java.util.List;

import projet.StageConnect.entites.Postuler;

public interface PostulerService {

	void applyForOffer(Long offerId, Long userId,String cvData);
	public List<String> getAllCvsByOffreId(Long offreId);

	String getStagiaireEmail(Long userId);
	 void deletePostulation(Long postulationId, Long userId);
	 public List<Postuler> getPostulationsByUserId(Long userId);
	 public List<Postuler> getPostulerByEntrepriseId(Long entrepriseId);
	 public List<Postuler> getPostulerByEntrepriseId2(Long entrepriseId);
	 String getStagiaireEmailByOfferId(Long offerId);
     void updateDecision(Long postulationId, Boolean decision);
     Postuler getPostulerByEmail(String email);
     Postuler save(Postuler postuler);
}
