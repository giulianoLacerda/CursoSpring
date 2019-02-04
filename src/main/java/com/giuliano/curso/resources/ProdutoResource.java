package com.giuliano.curso.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giuliano.curso.domain.Produto;
import com.giuliano.curso.dto.ProdutoDTO;
import com.giuliano.curso.resources.utils.URL;
import com.giuliano.curso.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	/**
	 * Método será invocado quando houver uma requisição GET para "/clientes".
	 * @return String 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/**
	 * Paginação para obter produtos.
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> finPage(
			@RequestParam(value="nome", defaultValue="0") String nome,
			@RequestParam(value="categorias", defaultValue="0") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="24") Integer size, // ou linhas por página.
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> produtos = service.search(URL.decodeParam(nome), ids, page, size, orderBy, direction);
		Page<ProdutoDTO> produtosDTO = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(produtosDTO);
	}

}
