package projet.StageConnect.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projet.StageConnect.entites.Postuler;
import projet.StageConnect.services.PostulerService;

@RestController
@RequestMapping("/postuler")
public class PostulerController {

  @Autowired
  private PostulerService postulerService;

  @PostMapping(path="/apply/{offerId}/{userId}/{cvData}")
  @CrossOrigin(origins = "http://localhost:8080")
  public ResponseEntity<String> applyForOffer( @PathVariable Long offerId, @PathVariable Long userId, @PathVariable String cvData)
  {
      postulerService.applyForOffer(offerId, userId,cvData);
      return ResponseEntity.ok("Application submitted successfully");
  }
  
  
  @GetMapping("/cvs/{offreId}")
  public List<String> getAllCvsByOffreId(@PathVariable Long offreId) {
      return postulerService.getAllCvsByOffreId(offreId);
  }
  
 @DeleteMapping("/delete/{postulationId}/{userId}")
 public ResponseEntity<String> deletePostulation(@PathVariable Long postulationId, @PathVariable Long userId)
 {
     postulerService.deletePostulation(postulationId, userId);
     return ResponseEntity.ok("Postulation deleted successfully");
 }

 @GetMapping("/entreprise/{entrepriseId}")
 public List<Postuler> getPostulerByEntrepriseId(@PathVariable Long entrepriseId) {
     return postulerService.getPostulerByEntrepriseId(entrepriseId);
 }
 @GetMapping("/entrepriset/{entrepriseId}")
 public List<Postuler> getPostulerByEntrepriseId2(@PathVariable Long entrepriseId) {
     return postulerService.getPostulerByEntrepriseId2(entrepriseId);
 }
 @GetMapping("/email/{offreId}")
 public ResponseEntity<String> getUserEmailByOffreId(@PathVariable Long offreId) {
     String userEmail = postulerService.getStagiaireEmailByOfferId(offreId);

     if (userEmail != null) {
         return ResponseEntity.ok(userEmail);
     } else {
         return ResponseEntity.notFound().build();
     }
 }

}

