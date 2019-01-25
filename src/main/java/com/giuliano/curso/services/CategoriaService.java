package com.giuliano.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giuliano.curso.domain.Categoria;
import com.giuliano.curso.repositories.CategoriaRepository;
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
		// Verifica se objeto existe ou não.
		buscar(obj.getId());
		return repo.save(obj); // Neste caso o id do objeto é mantido, pois a intenção é de atualizar este objeto.
	}

}
