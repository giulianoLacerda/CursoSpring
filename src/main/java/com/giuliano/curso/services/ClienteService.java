package com.giuliano.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giuliano.curso.domain.Cidade;
import com.giuliano.curso.domain.Cliente;
import com.giuliano.curso.domain.Endereco;
import com.giuliano.curso.domain.enums.TipoCliente;
import com.giuliano.curso.dto.ClienteDTO;
import com.giuliano.curso.dto.ClienteNewDTO;
import com.giuliano.curso.repositories.ClienteRepository;
import com.giuliano.curso.repositories.EnderecoRepository;
import com.giuliano.curso.services.exceptions.DataIntegrityException;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
	@Transactional
	public Cliente insert(Cliente cli) {
		cli.setId(null); // Garante que o id é null para que não seja feita atualização.
		repo.save(cli);
		enderecoRepository.saveAll(cli.getEnderecos());
		return cli;
	}
	
	
	/**
	 * Atualiza um cliente existente.
	 * @param cli
	 * @return
	 */
	public Cliente update(Cliente cli) {
		// Obtém objeto do banco de dados.
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
	
	public Cliente fromDTO(ClienteNewDTO cliDTO) {
		// Cria o cliente.
		Cliente cli = new Cliente(null, cliDTO.getNome(),
				cliDTO.getEmail(), cliDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(cliDTO.getTipo()));
		
		// Cria a cidade para depois criar o endereço.
		// Como o endereço recebe apenas a chave estrangeira com
		// o id da cidade, podemos criar um objeto cidade apenas
		// atribuindo à ele o ID.
		Cidade cid = new Cidade(cliDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, cliDTO.getLogradouro(),
				cliDTO.getNumero(), cliDTO.getComplemento(), cliDTO.getBairro(),
				cliDTO.getCep(), cli, cid);	
		
		// Adiciona o endereço na lista de endereços do cliente.
		cli.getEnderecos().add(end);
		
		// Adiciona o telefone na lista de telefones do cliente.
		cli.getTelefones().add(cliDTO.getTelefone1());
		if (cliDTO.getTelefone2()!=null)
			cli.getTelefones().add(cliDTO.getTelefone2());
		if (cliDTO.getTelefone3()!=null)
			cli.getTelefones().add(cliDTO.getTelefone3());
		
		return cli;
	}
	
	private void updateData(Cliente newCli, Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
	}

}
