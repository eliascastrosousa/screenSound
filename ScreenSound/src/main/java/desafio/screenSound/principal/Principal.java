package desafio.screenSound.principal;

import desafio.screenSound.model.Artista;
import desafio.screenSound.model.Categoria;
import desafio.screenSound.model.Musica;
import desafio.screenSound.repository.ArtistaRepository;
import desafio.screenSound.repository.MusicaRepository;
import desafio.screenSound.service.ConsultaChatGPT;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {
    private ArtistaRepository artistaRepository;
    private MusicaRepository musicaRepository;
    private Scanner sc = new Scanner(System.in);
    private String nomeArtista;
    private String nomeMusica;
    private List<Musica> musicas = new ArrayList<>();
    private Optional<Artista> artista;

    public Principal(ArtistaRepository artistaRepository, MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.musicaRepository = musicaRepository;
    }
    public void exibeMenu(){
        int opc = -1;
        while (opc != 9) {
            String menu =
                    """
                                    *** Screen Music ***
                                    1- Cadastrar artistas
                                    2- Cadastrar músicas
                                    3- Listar músicas
                                    4- Buscar músicas por artistas
                                    5- Pesquisar dados sobre um artista
                                    
                                    9- Sair
                            """;

            System.out.println("\n" + menu);
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    cadastrarArtista();
                    break;

                case 2:
                    cadastrarMusica();
                    break;

                case 3:
                    listarMusicas();
                    break;

                case 4:
                    listarMusicasPorArtista();
                    break;

                case 5:
                    pesquisarDadosSobreArtista();
                    break;
        }
        };

    }

    private void cadastrarArtista() {
        String cadastro = "S";
        while(cadastro.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do Artista: ");
            nomeArtista = sc.nextLine();
            System.out.println("Informe o tipo  de artista: (solo, dupla, banda)");
            var tipo = sc.nextLine();
            Categoria categoria = Categoria.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nomeArtista, categoria);
            artistaRepository.save(artista);
            System.out.println("\nCadastrar outro artista? (S/N)");
            cadastro = sc.nextLine();
        }
    }
    private void cadastrarMusica() {
        String cadastro = "S";
        while(cadastro.equalsIgnoreCase("s")) {

            System.out.println("Informe o nome do Artista: ");
            nomeArtista = sc.nextLine();
            Optional<Artista> artista = artistaRepository.findByTituloContainingIgnoreCase(nomeArtista);

            if (artista.isPresent()) {
                System.out.println("Informe o nome da musica: ");
                nomeMusica = sc.nextLine();
                if (musicaRepository.existsByTituloAndArtista(nomeMusica, artista.get())){
                    System.out.println("Musica ja cadastrada no sistema");
                }else {
                    var artistaEncontrado = artista.get();
                    Musica musica = new Musica(null, nomeMusica, artistaEncontrado);
                    musicaRepository.save(musica);
                    System.out.println("\nCadastrar outra musica? (S/N)");
                    cadastro = sc.nextLine();
                }
            }else {
                System.out.println("Artista nao encontrado. Deseja cadastrar novo artista? (S/N) ");
                cadastro = sc.nextLine();
                if (cadastro.equalsIgnoreCase("s")){
                    System.out.println("Informe o tipo  de artista: (solo, dupla, banda)");
                    var tipo = sc.nextLine();
                    Categoria categoria = Categoria.valueOf(tipo.toUpperCase());

                    Artista novoArtista = new Artista(nomeArtista, categoria);
                    artistaRepository.save(novoArtista);
                    System.out.println("Artista cadastrado!");

                    System.out.println("Informe o nome da musica: ");
                    nomeMusica = sc.nextLine();

                    if (musicaRepository.existsByTituloAndArtista(nomeMusica, novoArtista)){
                        System.out.println("Musica ja cadastrada no sistema");
                    }else {
                        Musica musica = new Musica(null, nomeMusica, novoArtista);
                        musicaRepository.save(musica);
                        System.out.println("Musica cadastrada com sucesso!");
                        System.out.println("\nCadastrar outra musica? (S/N)");
                        cadastro = sc.nextLine();
                    }
                }
            }
        }
    }
    private void listarMusicas() {
        musicas = musicaRepository.findAll();
        musicas.forEach(System.out::println);
    }
    private void listarMusicasPorArtista() {
        System.out.println("digite o nome do artista para buscar as musicas: ");
        nomeArtista = sc.nextLine();
        artista = artistaRepository.findByTituloContainingIgnoreCase(nomeArtista);

        if (artista.isPresent()){
            musicas = musicaRepository.findAllByArtista(artista.get());
            musicas.forEach(System.out::println);
        }
    }
    private void pesquisarDadosSobreArtista() {
        System.out.println("Digite o nome do artista que deseja pesquisar: ");
        nomeArtista = sc.nextLine();
        var descricao = ConsultaChatGPT.obterDescricao(nomeArtista);
        System.out.println("\n Aqui está uma breve descricao sobre "+nomeArtista + "\n");
        System.out.println(descricao);
    }




}

