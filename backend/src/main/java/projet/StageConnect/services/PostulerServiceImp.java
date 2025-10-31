package projet.StageConnect.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.StageConnect.Repositories.OffreRepository;
import projet.StageConnect.Repositories.PostulerRepository;
import projet.StageConnect.Repositories.UtilisateurRepository;
import projet.StageConnect.entites.Offre;
import projet.StageConnect.entites.Postuler;
import projet.StageConnect.entites.Utilisateur;

@Service
public class PostulerServiceImp implements PostulerService {

    private final PostulerRepository postulerRepository;
    private final OffreRepository offreRepository;
    private final UtilisateurRepository userRepository;

    @Autowired
    public PostulerServiceImp(PostulerRepository postulerRepository, OffreRepository offreRepository, UtilisateurRepository utilisateurRepository) {
        this.postulerRepository = postulerRepository;
        this.offreRepository = offreRepository;
        this.userRepository = utilisateurRepository;
    }

    @Override
    public void applyForOffer(Long offerId, Long userId, String cvData) {
        Utilisateur utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Offre offre = offreRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + offerId));

        
        Postuler postuler = new Postuler();
        postuler.setUtilisateur(utilisateur);
        postuler.setOffre(offre);
        postuler.setCv(cvData);
        postuler.setDecision(false); 
        postulerRepository.save(postuler);
    }

    @Override
    public List<String> getAllCvsByOffreId(Long offreId) {
        return postulerRepository.findAllCvsByOffreId(offreId);
    }

    @Override
    public String getStagiaireEmail(Long userId) {
        Utilisateur utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        
        if (utilisateur.getStagiaire() != null) {
            return utilisateur.getEmail();
        } else {
            throw new IllegalArgumentException("User with id " + userId + " is not a student");
        }
    }

    @Override
    @Transactional
    public void deletePostulation(Long postulationId, Long userId) {
        // Find all postulations based on offerId and userId
        List<Postuler> postulationsToDelete = postulerRepository.findAllByOffreIdAndUtilisateurId(postulationId, userId);

        // Check if there are postulations to delete
        if (!postulationsToDelete.isEmpty()) {
            // Delete all postulations
            postulerRepository.deleteAll(postulationsToDelete);
        }
    }
    

    public List<Postuler> getPostulationsByUserId(Long userId) {
        return postulerRepository.findByUtilisateur_Id(userId);
    }
    

    public List<Postuler> getPostulerByEntrepriseId(Long entrepriseId) {
        return postulerRepository.findPostulerByEntrepriseId(entrepriseId);
    }
    public List<Postuler> getPostulerByEntrepriseId2(Long entrepriseId) {
        return postulerRepository.findPostulerByEntrepriseId2(entrepriseId);
    }

    @Override
    public String getStagiaireEmailByOfferId(Long offerId) {
        java.util.Optional<Offre> optionalOffre = offreRepository.findById(offerId);

        if (optionalOffre.isPresent()) {
            Offre offre = optionalOffre.get();
            
            if (offre.getUtilisateur() != null) {
                return offre.getUtilisateur().getEmail();
            } else {
          
                return null;
            }
        } else {
            
            return null;
        }
    }
    
    @Override
    public void updateDecision(Long postulationId, Boolean decision) {
        Postuler postuler = postulerRepository.findById(postulationId)
                .orElseThrow(() -> new RuntimeException("Postulation not found with id: " + postulationId));


        postuler.setDecision(decision);


        postulerRepository.save(postuler);
    }
    @Override
    public Postuler getPostulerByEmail(String email) {
        return postulerRepository.findByUtilisateur_Email(email);
    }

    @Override
    public Postuler save(Postuler postuler) {
        return postulerRepository.save(postuler);
    }

    }

	
    

  


