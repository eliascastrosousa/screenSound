package desafio.screenSound.repository;

import desafio.screenSound.model.Artista;
import desafio.screenSound.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica,Long> {
    boolean existsByTituloAndArtista(String nomeMusica, Artista artista);

    List<Musica> findAllByArtista(Artista artista);
}
