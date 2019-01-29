package com.giuliano.curso.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.giuliano.curso.resources.ValidationError;
import com.giuliano.curso.services.exceptions.DataIntegrityException;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

/**
 * Intercepta as exceções geradas pelas requisições.
 * @author giuliano
 *
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	// Trata exceção quando o objeto da requisição não é encontrado.
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		// Cria o objeto de erro padrão e envia este objeto como resposta usando o código
		// 404 NOT FOUND.
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // Retorna a mensagem com status 404 contida em seu corpo os detalhes do erro.
	}
	
	// Trata exceção quando requisição não possui todos os campos necessários.
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		// Cria o objeto de erro padrão e envia este objeto como resposta usando o código
		// 400 BAD REQUEST.
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // Retorna a mensagem com status 404 contida em seu corpo os detalhes do erro.
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataIntegrity(MethodArgumentNotValidException e, HttpServletRequest request){
		
		// Cria o objeto de erro padrão e envia este objeto como resposta usando o código
		// 400 BAD REQUEST.
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		// Para cada campo de erro na mensagem de erro, converte para ValidationError.
		// Ou seja, teremos uma lista de erros de validação.
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // Retorna a mensagem com status 404 contida em seu corpo os detalhes do erro.
	}
	
	
}
