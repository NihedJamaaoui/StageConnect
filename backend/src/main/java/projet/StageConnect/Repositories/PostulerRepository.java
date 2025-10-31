package projet.StageConnect.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projet.StageConnect.entites.Postuler;

public interface PostulerRepository extends JpaRepository<Postuler, Long> {
	
    @Query("SELECT p.cv FROM Postuler p WHERE p.offre.id = :offreId")
    List<String> findAllCvsByOffreId(@Param("offreId") Long offreId);

	Postuler findByOffreIdAndUtilisateurId(Long offreId, Long utilisateurId);
	List<Postuler> findAllByOffreIdAndUtilisateurId(Long offreId, Long utilisateurId);
	List<Postuler> findByUtilisateur_Id(Long userId);
	
	
	@Query("SELECT p FROM Postuler p " +
	           "JOIN p.offre o " +
	           "JOIN o.utilisateur u " +
	           "WHERE u.entreprise.id = :entrepriseId AND p.decision = false")
	    List<Postuler> findPostulerByEntrepriseId(@Param("entrepriseId") Long entrepriseId);
	
	@Query("SELECT p FROM Postuler p " +
	           "JOIN p.offre o " +
	           "JOIN o.utilisateur u " +
	           "WHERE u.entreprise.id = :entrepriseId AND p.decision = true")
	    List<Postuler> findPostulerByEntrepriseId2(@Param("entrepriseId") Long entrepriseId);
	
	    @Query("SELECT p FROM Postuler p WHERE p.offre.id = :offreId")
	    Postuler findByOffreId(@Param("offreId") Long offreId);
	    @Query("SELECT p.utilisateur.email FROM Postuler p WHERE p.offre.id = :offerId")
	    String getStagiaireEmailByOfferId(@Param("offerId") Long offerId); 
	    Postuler save(Postuler postuler);
	    Postuler findByUtilisateur_Email(String email);
	}




