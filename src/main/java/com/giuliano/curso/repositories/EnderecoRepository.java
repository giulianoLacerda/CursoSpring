package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Endereco;

// Operações de acesso a dados referente as categorias.
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
