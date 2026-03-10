package com.literalura.LiterAlura.principal;

import com.literalura.LiterAlura.dto.DadosAutor;
import com.literalura.LiterAlura.dto.DadosLivro;
import com.literalura.LiterAlura.dto.DadosResultados;
import com.literalura.LiterAlura.model.Autor;
import com.literalura.LiterAlura.model.Livro;
import com.literalura.LiterAlura.repository.AutorRepository;
import com.literalura.LiterAlura.repository.LivroRepository;
import com.literalura.LiterAlura.service.ConsumoApi;
import com.literalura.LiterAlura.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ---------------------------
                    ESCOLHA O NÚMERO DA OPÇÃO:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                                       \s
                    0 - Sair
                    ---------------------------
                   \s""";

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o nome do livro que deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados("https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "%20"));
        var dados = conversor.obterDados(json, DadosResultados.class);

        if (dados.resultado().isEmpty()) {
            System.out.println("Livro não encontrado.");
        } else {
            DadosLivro dadosLivro = dados.resultado().get(0);


            if (livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo()) != null) {
                System.out.println("Este livro já está cadastrado no sistema!");
                return;
            }


            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Autor autor = autorRepository.findByNomeContainingIgnoreCase(dadosAutor.nome());

            if (autor == null) {
                autor = new Autor(dadosAutor);
                autorRepository.save(autor);
            }

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);
            livroRepository.save(livro);

            System.out.println("\n----- LIVRO ENCONTRADO -----");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getNumeroDownloads());
            System.out.println("----------------------------\n");
        }
    }

    private void listarLivrosRegistrados() {
        var livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(l -> System.out.println(
                    "Livro: " + l.getTitulo() + " | Autor: " + l.getAutor().getNome() + " | Idioma: " + l.getIdioma()
            ));
        }
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
        } else {
            autores.forEach(a -> System.out.println("Autor: " + a.getNome() + " | Nascimento: " + a.getAnoNascimento()));
        }
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine();

        var autores = autorRepository.buscarAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado para o ano de " + ano);
        } else {
            autores.forEach(a -> System.out.println("Autor: " + a.getNome() + " (Falecimento: " + (a.getAnoFalecimento() == null ? "Vivo" : a.getAnoFalecimento()) + ")"));
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            Digite o idioma para busca:
            es - espanhol
            en - inglês
            fr - francês
            pt - português
            """);
        var idioma = leitura.nextLine();
        var livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
        } else {
            livros.forEach(l -> System.out.println("Título: " + l.getTitulo() + " [" + l.getIdioma() + "]"));
        }
    }
}