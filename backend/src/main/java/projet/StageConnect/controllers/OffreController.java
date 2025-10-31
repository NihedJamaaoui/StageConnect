package projet.StageConnect.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.StageConnect.entites.Offre;
import projet.StageConnect.services.OffreService;

@RestController
@RequestMapping("/offre")
@CrossOrigin(origins = "http://localhost:3000")
public class OffreController {

    @Autowired
    private OffreService offreService;

    @PostMapping("createOffre/{userId}")
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre, @PathVariable Long userId) {
        Offre createdOffre = offreService.createOffre(offre, userId);
        if (createdOffre != null) {
            return new ResponseEntity<>(createdOffre, HttpStatus.CREATED); 
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping("/updateOffre/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable Long id, @RequestBody Offre updatedOffre) {
        Offre savedOffre = offreService.updateOffre(id, updatedOffre);

        if (savedOffre != null) {
            return new ResponseEntity<>(savedOffre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllOffres")
    public List<Offre> getAllOffres() {
        return offreService.getAllOffres();
    }

    @DeleteMapping("/deleteOffre/{id}")
    public ResponseEntity<String> deleteOffre(@PathVariable Long id) {
        offreService.deleteOffre(id);
        return ResponseEntity.ok("Offre deleted successfully");
    }
    
    @GetMapping("/user/{userId}")
    public List<Offre> getOffersByUserId(@PathVariable Long userId) {
        return offreService.getOffersByUserId(userId);
    }
   
    
  /*  @GetMapping("/getAllStagiaireOffres")
    public ResponseEntity<List<Offre>> getAllStagiaireOffres() {
        List<Offre> stagiaireOffres = offreService.getAllStagiaireOffres();
        return new ResponseEntity<>(stagiaireOffres, HttpStatus.OK);
    }*/

    @GetMapping("/getAllEntrepriseOffres")
    public ResponseEntity<List<Offre>> getAllEntrepriseOffres() {
        List<Offre> entrepriseOffres = offreService.getAllEntrepriseOffres();
        return new ResponseEntity<>(entrepriseOffres, HttpStatus.OK);
    }
    
    
    @GetMapping("/{offreId}/entrepriseId")
    public Long getEntrepriseIdByOffreId(@PathVariable Long offreId) {
        return offreService.getEntrepriseIdByOffreId(offreId);
    }
}