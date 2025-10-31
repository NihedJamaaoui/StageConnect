package projet.StageConnect.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projet.StageConnect.entites.Utilisateur;



public interface UtilisateurRepository  extends JpaRepository <Utilisateur , Long>{
	//login
	public List<Utilisateur> findByEmailAndPassword(String email,String password);	
    public Utilisateur findByEmail(String email);
	public Utilisateur findById(long id);
	
	

	@Query("SELECT s.cv FROM Stagiaire s WHERE s.id = :userId")
	String getCvOfStagiaireByUserId(@Param("userId") Long userId);


    @Query("SELECT u.email FROM Utilisateur u JOIN u.offres o WHERE o.id = :offreId")
    String findUserEmailByOffreId(@Param("offreId") Long offreId);
    
    
    @Query("SELECT u.stagiaire.id FROM Utilisateur u WHERE u.id = :userId")
    Long findStagiaireIdByUserId(@Param("userId") Long userId);
    
    @Query("SELECT u.entreprise.id FROM Utilisateur u WHERE u.id = :userId")
    Long findEntrepriseIdByUserId(@Param("userId") Long userId);
}

