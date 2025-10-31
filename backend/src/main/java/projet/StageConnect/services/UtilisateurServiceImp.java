package projet.StageConnect.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import projet.StageConnect.Repositories.EntrepriseRepository;
import projet.StageConnect.Repositories.StagiaireRepository;
import projet.StageConnect.Repositories.UtilisateurRepository;
import projet.StageConnect.entites.Entreprise;
import projet.StageConnect.entites.Stagiaire;
import projet.StageConnect.entites.Utilisateur;

@Service
public class UtilisateurServiceImp implements UtilisateurService {

    @Autowired
    private UtilisateurRepository userRepository;
    @Autowired
    private StagiaireRepository stagiaireRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    

    @Override
    public List<Utilisateur> findByEmailAndPassword(String email, String plaintextPassword) {
        Utilisateur utilisateur = userRepository.findByEmail(email);

        if (utilisateur != null && bCryptPasswordEncoder.matches(plaintextPassword, utilisateur.getPassword())) {
            return Collections.singletonList(utilisateur);
        }

        return Collections.emptyList();
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        String plainPassword = utilisateur.getPassword();
        String encryptedPassword = bCryptPasswordEncoder.encode(plainPassword);
        utilisateur.setPassword(encryptedPassword);
        Utilisateur savedUser = userRepository.save(utilisateur);
        return savedUser;
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findByEmail(email);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (utilisateur.getStagiaire() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STAGIAIRE"));
        }

        if (utilisateur.getEntreprise() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ENTREPRISE"));
        }

        return new User(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
    }

    @Override
    public String getCvOfStagiaireByUserId(Long userId) {
        return userRepository.getCvOfStagiaireByUserId(userId);
    }

    public String findUserEmailByOffreId(Long offreId) {
        return userRepository.findUserEmailByOffreId(offreId);
    }

    public Long findStagiaireIdByUserId(Long userId) {
        return userRepository.findStagiaireIdByUserId(userId);
    }

    public Long findEntrepriseIdByUserId(Long userId) {
        return userRepository.findEntrepriseIdByUserId(userId);
    }
    @Override
	public Utilisateur UserById(Long id) {
	    Optional<Utilisateur> optionalUser = userRepository.findById(id);

	    // Vérification si l'utilisateur existe
	    if (optionalUser.isPresent()) {
	        return optionalUser.get(); // Retourne l'utilisateur trouvé à partir de l'Optional
	    } else {
	        return null; // Aucun utilisateur trouvé, renvoie null
	    }
	}

	@Override
	public void updateUtilisateur(Long id, Utilisateur utilisateur) {
	    Optional<Utilisateur> optionalExistingUtilisateur = userRepository.findById(id);

	    if (optionalExistingUtilisateur.isPresent()) {
	        Utilisateur existingUtilisateur = optionalExistingUtilisateur.get();
	        Stagiaire listeStagiaires = existingUtilisateur.getStagiaire();
	        Entreprise listeEntreprises = existingUtilisateur.getEntreprise();

	        if (listeStagiaires != null) {
	            // S'il y a des stagiaires, mettez à jour les informations du premier stagiaire
	         
	            listeStagiaires.setNom(utilisateur.getStagiaire().getNom());
	            listeStagiaires.setPrenom(utilisateur.getStagiaire().getPrenom());
	            listeStagiaires.setDate_naissance(utilisateur.getStagiaire().getDate_naissance());
	            listeStagiaires.setCv(utilisateur.getStagiaire().getCv());

	           
	            
	            stagiaireRepository.save(listeStagiaires);
	        } else if (listeEntreprises != null) {
	          
	        	listeEntreprises.setNom(utilisateur.getEntreprise().getNom());
	        	listeEntreprises.setAdresse(utilisateur.getEntreprise().getAdresse());
	        	listeEntreprises.setTelephone(utilisateur.getEntreprise().getTelephone());
	            
	            
	            entrepriseRepository.save(listeEntreprises);
	        } else {
	            
	        }

	      
	        existingUtilisateur.setEmail(utilisateur.getEmail());
	        existingUtilisateur.setImage(utilisateur.getImage());
	        existingUtilisateur.setPassword(utilisateur.getPassword());

	        
	        userRepository.save(existingUtilisateur);
	    } else {
	        // Gérer le cas où aucun utilisateur n'est trouvé avec cet ID
	        // Vous pouvez lever une exception, renvoyer un message d'erreur, etc.
	    }
	}


}
