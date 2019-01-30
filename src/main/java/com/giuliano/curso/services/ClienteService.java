package com.giuliano.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Cliente;
import com.giuliano.curso.dto.ClienteDTO;
import com.giuliano.curso.repositories.ClienteRepository;
import com.giuliano.curso.services.exceptions.DataIntegrityException;
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
	
	/**
	 * Inserção de um novo usuário.
	 * @param cli
	 * @return
	 */
	public Cliente insert(Cliente cli) {
		cli.setId(null); // Garante que o id é null para que não seja feita atualização.
		return repo.save(cli);
	}
	
	
	/**
	 * Atualiza um cliente existente.
	 * @param cli
	 * @return
	 */
	public Cliente update(Cliente cli) {
		// Verifica se o objeto existe.
		Cliente newCli = buscar(cli.getId());
		updateData(newCli, cli);
		return repo.save(newCli);
	}
	
	
	public void delete(Integer id) {
		// Verifica se o objeto existe.
		buscar(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			// Caso o cliente esteja relacionado à outros objetos.
			throw new DataIntegrityException("Não é possível excluir um"
					+ "cliente que possui pedidos.");
			
		}
	}
	
	/**
	 * Retorna toda a lista de clientes.
	 * @return
	 */
	public List<Cliente> clientes(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage,
			String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO cliDTO) {
		return new Cliente(cliDTO.getId(),cliDTO.getNome(), cliDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCli, Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
	}

}
