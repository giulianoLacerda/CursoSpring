package com.giuliano.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Produto;
import com.giuliano.curso.repositories.ProdutoRepository;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private ProdutoRepository repo;
	
	/**
	 * Busca objeto pelo identificador.
	 * @param id Identificador.
	 * @return Categoria.
	 */
	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Produto.class.getName()));
	}

}
