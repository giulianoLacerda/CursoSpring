package com.giuliano.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Categoria;
import com.giuliano.curso.domain.Produto;
import com.giuliano.curso.repositories.CategoriaRepository;
import com.giuliano.curso.repositories.ProdutoRepository;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/**
	 * Busca objeto pelo identificador.
	 * @param id Identificador.
	 * @return Categoria.
	 */
	public Produto find(Integer id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Produto.class.getName()));
	}
	
	
	/**
	 * Dado os atributos, o método retorna a paginação de todos os produtos com nome "nome", 
	 * que possuem pelo menos uma categoria na lista de categorias "ids".
	 * @param nome
	 * @param ids
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		// Busca lista de categorias pelos seus ids.
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		// Retorna a paginação dos produtos com com nome "nome" que possuem pelo menos uma categoria
		// na lista de categorias "categorias".
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
