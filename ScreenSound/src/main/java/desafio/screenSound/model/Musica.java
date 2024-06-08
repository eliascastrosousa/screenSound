package desafio.screenSound.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "musicas")
@NoArgsConstructor
@AllArgsConstructor

public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Artista artista;

    public void exibe(){
        System.out.println("Artista: "+artista + " Musica: "+titulo);
    }

    @Override
    public String toString() {
        return "Musica: " + titulo +
                ", Artista: " + artista;
    }
}
