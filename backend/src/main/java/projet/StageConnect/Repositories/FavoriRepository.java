package projet.StageConnect.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.StageConnect.entites.Favori;

public interface FavoriRepository extends JpaRepository<Favori, Long> {
	List<Favori> findByStagiaireId(Long stagiaireId);
}
