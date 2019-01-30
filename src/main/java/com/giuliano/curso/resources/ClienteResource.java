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
import com.giuliano.curso.domain.Cliente;
import com.giuliano.curso.dto.ClienteDTO;
import com.giuliano.curso.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	/**
	 * Método será invocado quando houver uma requisição GET para "/clientes".
	 * @return String 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/**
	 * Método insert para inserir um novo Cliente. 
	 * @param obj
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO obj){
		Cliente objCategoria = service.insert(service.fromDTO(obj));
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(objCategoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Método para atualizar um novo cliente. Recebe o conteúdo que
	 * atualizará(body) e o identificador do objeto a ser atualizado.
	 * @param obj
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		objDTO.setId(id);
		service.update(service.fromDTO(objDTO));
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método para deletar um cliente existente. Recebe o id do cliente
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
	 * Método para obter a lista de todos os clientes
	 * @return
	 */
	/*@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> finAll() {
		List<Clientes> clientes = service.clientes();
		List<ClienteDTO> clientesDTO = clientes.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(ClientesDTO);
	}*/
	
	/**
	 * Paginação para obter clientes.
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="24") Integer size,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		// Obtém a paginação com lista de clientes.
		Page<Cliente> clientes = service.findPage(page, size, orderBy, direction);
		
		// Converte de Cliente para ClienteDTO cada um dos objetos paginados.
		Page<ClienteDTO> clientesDTO = clientes.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(clientesDTO);
	}

}
