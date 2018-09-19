package com.giuliano.curso;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.giuliano.curso.domain.Categoria;
import com.giuliano.curso.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	/**
	 * Método executado quando a aplicação é iniciada.
	 */
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
	} 
}
