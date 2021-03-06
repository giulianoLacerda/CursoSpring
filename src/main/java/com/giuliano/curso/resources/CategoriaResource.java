package com.giuliano.curso.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giuliano.curso.domain.Categoria;
import com.giuliano.curso.dto.CategoriaDTO;
import com.giuliano.curso.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	/**
	 * Método será invocado quando houver uma requisição GET para "/categorias/{id}".
	 * @return String 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/**
	 * Método insert para inserir uma nova categoria. 
	 * @param obj
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO obj){
		Categoria objCategoria = service.insert(service.fromDTO(obj));
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(objCategoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Método para atualizar uma nova categoria. Recebe o conteúdo que
	 * atualizará(body) e o identificador do objeto a ser atualizado.
	 * @param obj
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		objDTO.setId(id);
		service.update(service.fromDTO(objDTO));
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método para deletar uma categoria existente. Recebe o id da categoria
	 * e então exclui a mesma.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método para obter a lista de todas as categorias.
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> finAll() {
		List<Categoria> categorias = service.categorias();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	/**
	 * Paginação para obter categorias.
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> finPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="24") Integer size,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Categoria> categorias = service.findPage(page, size, orderBy, direction);
		Page<CategoriaDTO> categoriasDTO = categorias.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(categoriasDTO);
	}

}
