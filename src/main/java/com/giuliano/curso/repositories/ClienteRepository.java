package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.giuliano.curso.domain.Cliente;

// Operações de acesso a dados referente as categorias.
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	// Busca cliente por email.
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
