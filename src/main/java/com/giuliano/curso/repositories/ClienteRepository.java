package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Cliente;

// Operações de acesso a dados referente as categorias.
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
