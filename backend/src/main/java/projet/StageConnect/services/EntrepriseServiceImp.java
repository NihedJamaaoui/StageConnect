package projet.StageConnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet.StageConnect.Repositories.EntrepriseRepository;


import projet.StageConnect.entites.Entreprise;


@Service
public class EntrepriseServiceImp implements EntrepriseService {

	 private final EntrepriseRepository entrepriseRepository;

	    @Autowired
	    public EntrepriseServiceImp(EntrepriseRepository entrepriseRepository) {
	        this.entrepriseRepository = entrepriseRepository;
	    }

	    @Override
	    public Entreprise createEntreprise(Entreprise entreprise) {
	        
	        return entrepriseRepository.save(entreprise);
	    }
}
		
	

	

