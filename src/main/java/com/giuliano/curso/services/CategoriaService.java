package com.giuliano.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Categoria;
import com.giuliano.curso.repositories.CategoriaRepository;
import com.giuliano.curso.services.exceptions.DataIntegrityException;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private CategoriaRepository repo;
	
	
	/**
	 * Busca uma categoria pelo identificador.
	 * @param id Identificador.
	 * @return Categoria.
	 */
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
	}

	
	/**
	 * Inserção de uma nova categoria.
	 * @param obj
	 * @return
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null); // Garante que o id é null para que não seja feita atualização ao invés de inserção.
		return repo.save(obj);
	}
	
	/**
	 * Atualização de uma categoria existente.
	 * @param obj
	 * @return
	 */
	public Categoria update(Categoria obj) {
		// Verifica se objeto existe.
		buscar(obj.getId());
		return repo.save(obj); // Neste caso o id do objeto é mantido, pois a intenção é de atualizar este objeto.
	}
	
	
	public void delete(Integer id) {
		// Verifica se objeto existe.
		buscar(id);
		
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) { 
			// Caso a categoria esteja relacionada à outros objetos, não permite exclusão e lança exceção.
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
			
		}
	}
	
	/**
	 * Retorna toda a lista de categorias.
	 * @return
	 */
	public List<Categoria> categorias(){
		return repo.findAll();
	}
	
	/**
	 * Paginação para busca de categorias.
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}
