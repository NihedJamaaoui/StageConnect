package projet.StageConnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projet.StageConnect.Repositories.OffreRepository;
import projet.StageConnect.Repositories.StagiaireRepository;
import projet.StageConnect.entites.Favori;
import projet.StageConnect.entites.Offre;
import projet.StageConnect.entites.Stagiaire;
import projet.StageConnect.services.FavoriService;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriController {

    private final FavoriService favoriService;
    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private StagiaireRepository stagiaireRepository;
    @Autowired
    public FavoriController(FavoriService favoriService) {
        this.favoriService = favoriService;
    }

    @GetMapping
    public List<Favori> getAllFavorites() {
        return favoriService.getAllFavorites();
    }

    @GetMapping("/{id}")
    public Favori getFavoriteById(@PathVariable Long id) {
        return favoriService.getFavoriteById(id);
    }

    @PostMapping
    public Favori saveFavorite(@RequestBody Favori favori) {
        return favoriService.saveFavorite(favori);
    }
    //@CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable Long id) {
        favoriService.deleteFavorite(id);
    }
    @GetMapping("/check-favorites")
    public List<Favori> checkFavorites() {
        List<Favori> favorites = favoriService.getAllFavorites();
        System.out.println(favorites); // Log the fetched favorites to check their structure
        return favorites; // Return favorites if needed for testing purposes
    }
    @PostMapping("/addFavorite")
    public ResponseEntity<Favori> addFavorite(@RequestParam Long offreId, @RequestParam Long stagiaireId) {
        Offre offre = null;
        Stagiaire stagiaire = null;

        try {
            offre = offreRepository.findById(offreId).orElse(null);
            stagiaire = stagiaireRepository.findById(stagiaireId).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (offre != null && stagiaire != null) {
            Favori favori = new Favori(stagiaire, offre);
            Favori savedFavori = favoriService.saveFavorite(favori);

            return new ResponseEntity<>(savedFavori, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/{userId}/favorites")
    public List<Favori> getFavoritesByUserId(@PathVariable Long userId) {
        return favoriService.getFavoritesByUserId(userId);
    }


}
