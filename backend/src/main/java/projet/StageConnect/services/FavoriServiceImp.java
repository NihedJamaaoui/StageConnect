package projet.StageConnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.StageConnect.entites.Favori;
import projet.StageConnect.Repositories.FavoriRepository;

import java.util.List;

@Service
public class FavoriServiceImp implements FavoriService {

    private final FavoriRepository favoriRepository;

    @Autowired
    public FavoriServiceImp(FavoriRepository favoriRepository) {
        this.favoriRepository = favoriRepository;
    }

    @Override
    public List<Favori> getAllFavorites() {
        return favoriRepository.findAll();
    }

    @Override
    public Favori getFavoriteById(Long id) {
        return favoriRepository.findById(id).orElse(null);
    }

    @Override
    public Favori saveFavorite(Favori favori) {
        return favoriRepository.save(favori);
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriRepository.deleteById(id);
    }
    @Override
    public List<Favori> getFavoritesByUserId(Long userId) {
        
        return favoriRepository.findByStagiaireId(userId); 
    }
}
