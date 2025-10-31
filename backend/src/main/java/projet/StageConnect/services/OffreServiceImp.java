package projet.StageConnect.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.StageConnect.Repositories.OffreRepository;
import projet.StageConnect.Repositories.PostulerRepository;
import projet.StageConnect.Repositories.UtilisateurRepository;
import projet.StageConnect.entites.Offre;
import projet.StageConnect.entites.Postuler;
import projet.StageConnect.entites.Utilisateur;

@Service
public class OffreServiceImp implements OffreService {
	
	@Autowired 
	private OffreRepository offreRespository;
	@Autowired 
	private UtilisateurRepository userRepository;
	@Autowired 
	private PostulerRepository postulerRepository;
	
	@Override
	public Offre createOffre(Offre off, Long id) {
	    Optional<Utilisateur> userOptional = userRepository.findById(id);

	    if (userOptional.isPresent()) {
	        Utilisateur utilisateur = userOptional.get();
	        off.setUtilisateur(utilisateur);
	        return offreRespository.save(off);
	    } else {
	        return null;
	    }
	}


	@Override
    public Offre updateOffre(Long id, Offre off) {
        Optional<Offre> existingOffreOptional = offreRespository.findById(id);

        if (existingOffreOptional.isPresent()) {
            Offre existingOffre = existingOffreOptional.get();
            existingOffre.setTitle(off.getTitle());
            existingOffre.setDescription(off.getDescription());
            existingOffre.setDate(off.getDate());
            existingOffre.setAdr(off.getAdr());

            return offreRespository.save(existingOffre);
        } else {
            return null;
        }
    }

	
	    @Override
	    public void deleteOffre(Long id) {
	    	offreRespository.deleteById(id);
	    }

		@Override
		public List<Offre> getAllOffres() {
		
			return offreRespository.findAll();
		}
		
		 @Override
		 public List<Offre> getOffersByUserId(Long userId) {
		    return offreRespository.findByUserId(userId);
		 }
		


		  @Override
		  public List<Offre> getAllEntrepriseOffres() {
			  return offreRespository.findByUtilisateur_EntrepriseIsNotNull();
		  }

		  
		    public Long getEntrepriseIdByOffreId(Long offreId) {
		        return offreRespository.findEntrepriseIdByOffreId(offreId);
		    }
}
