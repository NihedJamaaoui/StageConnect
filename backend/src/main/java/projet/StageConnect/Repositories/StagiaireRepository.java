package projet.StageConnect.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.StageConnect.entites.Stagiaire;


public interface StagiaireRepository  extends JpaRepository <Stagiaire , Long>{
	
}
