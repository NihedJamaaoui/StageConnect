package projet.StageConnect.services;

import projet.StageConnect.entites.Favori;

import java.util.List;

public interface FavoriService {
    List<Favori> getAllFavorites();
    List<Favori>getFavoritesByUserId(Long userId);
    Favori getFavoriteById(Long id);
    Favori saveFavorite(Favori favori);
    void deleteFavorite(Long id);
}
