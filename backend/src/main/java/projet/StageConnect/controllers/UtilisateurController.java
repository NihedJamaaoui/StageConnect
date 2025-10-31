package projet.StageConnect.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projet.StageConnect.services.EntrepriseService;
import projet.StageConnect.services.StagiaireService;
import projet.StageConnect.services.UtilisateurService;
import projet.StageConnect.services.UtilisateurServiceImp;
import projet.StageConnect.entites.Entreprise;
import projet.StageConnect.entites.Stagiaire;
import projet.StageConnect.entites.Utilisateur;
import projet.StageConnect.requests.EmailAndPasswordRequest;
import projet.StageConnect.security.SecurityConstraints;
@RestController
@RequestMapping("/utilisateur")
//@CrossOrigin(origins = "8080")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateurController {
	
	 private final UtilisateurService utilisateurService;
	    private final StagiaireService stagiaireService;
	    private final EntrepriseService entrepriseService;// Inject StagiaireService
	    private final  UtilisateurServiceImp utilisateurServiceImp;

	@Autowired
	public UtilisateurController(UtilisateurService utilisateurService, StagiaireService stagiaireService,EntrepriseService entrepriseService ,UtilisateurServiceImp utilisateurServiceImp ) {
	    this.utilisateurService = utilisateurService;
	    this.stagiaireService = stagiaireService;
	    this.entrepriseService =  entrepriseService;
	    this.utilisateurServiceImp = utilisateurServiceImp;
	}

	@GetMapping
	public  List<Utilisateur> getAllUtilisateurs() {
		return utilisateurService.getAllUtilisateurs();
	}

	
	@PostMapping(path="/RegisterS", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_PDF_VALUE })
	@CrossOrigin(origins = "http://localhost:8080")
	public ResponseEntity<Stagiaire> registerStagiaire(
	        @RequestPart("nom") String nom,
	        @RequestPart("prenom") String prenom,
	        @RequestPart("email") String email,
	        @RequestPart("password") String password,
	        @RequestPart("date_naissance") String date_naissance,
	        @RequestPart("image") MultipartFile image,
			@RequestPart("cv") MultipartFile cv){
	    try {
	        Stagiaire stagiaire = new Stagiaire();
		           
	        stagiaire.setNom(nom); 
	        stagiaire.setPrenom(prenom);
	        stagiaire.setDate_naissance(date_naissance);
	        String CVFileName = cv.getOriginalFilename();
	        stagiaire.setCv(CVFileName);
	        Stagiaire savedStagiaire = stagiaireService.createStagiaire(stagiaire);
	        if (savedStagiaire != null) {
	            Utilisateur utilisateur = new Utilisateur();
	            utilisateur.setStagiaire(savedStagiaire);
	            utilisateur.setEmail(email);
	            utilisateur.setPassword(password);
	            String originalFileName = image.getOriginalFilename();
	            utilisateur.setImage(originalFileName);
	            utilisateurService.createUtilisateur(utilisateur);
	            System.out.print(utilisateur);
	        }
	        return new ResponseEntity<>(savedStagiaire, HttpStatus.CREATED);
	    } catch (Exception ex) {ex.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

	
	@PostMapping(path = "/RegisterE", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@CrossOrigin(origins = "http://localhost:8080")
	public ResponseEntity<Entreprise> registerEntrprise(
	        @RequestPart("adresse") String adresse,
	        @RequestPart("telephone") String telephone,
	        @RequestPart("nom") String nom,
	        @RequestPart("email") String email,
	        @RequestPart("password") String password,
	        @RequestPart("image") MultipartFile image) {
	    try {
	        Entreprise entreprise = new Entreprise();
	        entreprise.setAdresse(adresse);
	        entreprise.setTelephone(telephone);
	        entreprise.setNom(nom);

	        Entreprise savedEntreprise = entrepriseService.createEntreprise(entreprise);

	        if (savedEntreprise != null) {
	            Utilisateur utilisateur = new Utilisateur();
	            utilisateur.setEntreprise(savedEntreprise);
	            utilisateur.setEmail(email);
	            utilisateur.setPassword(password);
	            String originalFileName = image.getOriginalFilename();
	            utilisateur.setImage(originalFileName);

	            utilisateurService.createUtilisateur(utilisateur);

	            System.out.print(utilisateur);
	        }
	        return new ResponseEntity<>(savedEntreprise, HttpStatus.CREATED);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	           


	@PostMapping(path = "/Login")
	@CrossOrigin(origins = "http://localhost:8080")
	public ResponseEntity<Map<String, Object>> findByEmailAndPassword(@RequestBody EmailAndPasswordRequest emailpassreq) {

	    List<Utilisateur> utilisateurs = utilisateurService.findByEmailAndPassword(emailpassreq.getEmail(), emailpassreq.getPassword());

	    if (utilisateurs.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        Utilisateur utilisateur = utilisateurs.get(0);

	        if (utilisateur.getStagiaire() != null) {
	            System.out.println("Login as Stagiaire: " + utilisateur.getStagiaire().getNom());
	        } else if (utilisateur.getEntreprise() != null) {
	            System.out.println("Login as Entreprise: " + utilisateur.getEntreprise().getNom());
	        }

	        // Generate JWT Token
	        String token = generateJwtToken(utilisateur.getEmail());

	        // Build the response body
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("token", token);
	        responseBody.put("user", utilisateurs);

	        return ResponseEntity.ok(responseBody);
	    }
	}

	private String generateJwtToken(String email) {
	    String token = Jwts.builder()
	            .setSubject(email)
	            .setExpiration(new Date(System.currentTimeMillis() + 9999999))  
	            .signWith(SignatureAlgorithm.HS512, SecurityConstraints.SECRET_JWT)
	            .compact();

	    System.out.println("Generated JWT Token: " + token);
	    return token;
	}

	@GetMapping("/getUserByEmail")
	public ResponseEntity<Utilisateur> getUserByEmail(@RequestParam String email) {
	    Utilisateur user = utilisateurService.findByEmail(email);
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	

    @GetMapping("/cv/{userId}")
    public ResponseEntity<String> getCvOfStagiaireByUserId(@PathVariable Long userId) {
        String cv = utilisateurService.getCvOfStagiaireByUserId(userId);
        if (cv != null) {
            return new ResponseEntity<>(cv, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}

    @GetMapping("/email/{offreId}")
    public ResponseEntity<String> getUserEmailByOffreId(@PathVariable Long offreId) {
        String userEmail = utilisateurService.findUserEmailByOffreId(offreId);

        if (userEmail != null) {
            return ResponseEntity.ok(userEmail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/entrepriseId/{userId}")
    public Long getEntrepriseIdByUserId(@PathVariable Long userId) {
        return utilisateurService.findEntrepriseIdByUserId(userId);
    }
    @GetMapping("/getById/{id}")
	public Utilisateur getbyid(@PathVariable Long id) {
	    Utilisateur users = utilisateurService.UserById(id);
	        return users;
	    
	}
	@PutMapping(path="/update/{id}")
	@CrossOrigin(origins = "http://localhost:8080")
	public ResponseEntity<Void> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
	    try {
	        utilisateurService.updateUtilisateur(id, utilisateur);
	        return ResponseEntity.ok().build(); // Réponse OK si la mise à jour est réussie
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur interne du serveur
	    }
	}

}
