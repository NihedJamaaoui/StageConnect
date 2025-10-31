package projet.StageConnect.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import projet.StageConnect.entites.Utilisateur; 

public interface UtilisateurService extends UserDetailsService{

	
	public List<Utilisateur> findByEmailAndPassword(String email, String plaintextPassword);
	public Utilisateur findByEmail(String email);
	 
	public Utilisateur createUtilisateur(Utilisateur utilisateur);
	public List<Utilisateur> getAllUtilisateurs();

	String getCvOfStagiaireByUserId(Long userId);
	
	//Utilisateur registerUtilisateur(Utilisateur utilisateur);
	//public List<Utilisateur> findById(Long id);
	//public void registerUtilisateur(Utilisateur utilisateur);


	public String findUserEmailByOffreId(Long offreId);
	
	public Long findStagiaireIdByUserId(Long userId);
	public Long findEntrepriseIdByUserId(Long userId);
	public Utilisateur UserById(Long id);
	
	public void updateUtilisateur(Long id, Utilisateur utilisateur);
	
}
