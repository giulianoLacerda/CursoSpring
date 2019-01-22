package com.giuliano.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Pedido;
import com.giuliano.curso.repositories.PedidoRepository;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private PedidoRepository repo;
	
	/**
	 * Busca objeto pelo identificador.
	 * @param id Identificador.
	 * @return Categoria.
	 */
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
	}

}
