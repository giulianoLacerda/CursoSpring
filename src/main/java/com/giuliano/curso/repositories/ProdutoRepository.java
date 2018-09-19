package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
