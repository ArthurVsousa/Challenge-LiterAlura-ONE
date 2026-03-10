package com.literalura.LiterAlura.repository;

import com.literalura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);

    Livro findByTituloContainingIgnoreCase(String titulo);
}