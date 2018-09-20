package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
