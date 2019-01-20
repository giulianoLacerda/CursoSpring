package com.giuliano.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Cliente;
import com.giuliano.curso.repositories.ClienteRepository;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private ClienteRepository repo;
	
	/**
	 * Busca objeto pelo identificador.
	 * @param id Identificador.
	 * @return Cliente.
	 */
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	}

}
