package desafio.screenSound.repository;

import desafio.screenSound.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByTituloContainingIgnoreCase(String nomeArtista);

}
