package com.giuliano.curso.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	/**
	 * Método será invocado quando houver uma requisição GET para "/categorias".
	 * @return String 
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String lsita() {
		return "REST está funcionando!";
	}

}
