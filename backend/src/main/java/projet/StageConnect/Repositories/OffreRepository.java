package projet.StageConnect.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projet.StageConnect.entites.Offre;

public interface OffreRepository  extends JpaRepository <Offre , Long> {
	
	  @Query("SELECT uo FROM Offre uo WHERE uo.utilisateur.id = :userId")
	    List<Offre> findByUserId(@Param("userId") Long userId);
	  
	    List<Offre> findByUtilisateur_StagiaireIsNotNull();
	    
	    List<Offre> findByUtilisateur_EntrepriseIsNotNull();
	    
	    List<Offre> findByUtilisateur_Entreprise_Id(Long entrepriseId);
	    
	    @Query("SELECT o.utilisateur.entreprise.id FROM Offre o WHERE o.id = :offreId")
	    Long findEntrepriseIdByOffreId(@Param("offreId") Long offreId);
}
