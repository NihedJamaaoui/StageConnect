package projet.StageConnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.StageConnect.Repositories.StagiaireRepository;
import projet.StageConnect.entites.Stagiaire;


@Service
public class StagiaireServiceImp implements StagiaireService {

	 private final StagiaireRepository stagiaireRepository;

	    @Autowired
	    public StagiaireServiceImp(StagiaireRepository stagiaireRepository) {
	        this.stagiaireRepository = stagiaireRepository;
	    }

	    @Override
	    public Stagiaire createStagiaire(Stagiaire stagiaire) {
	        
	        return stagiaireRepository.save(stagiaire);
	    }
	
	
	
	

}