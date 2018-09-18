package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
