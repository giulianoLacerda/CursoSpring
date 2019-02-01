package com.giuliano.curso.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.giuliano.curso.domain.enums.TipoCliente;
import com.giuliano.curso.dto.ClienteNewDTO;
import com.giuliano.curso.resources.FieldMessage;
import com.giuliano.curso.services.validation.utils.CpfCnpj;
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// Verifica se é pessoa física e se o cpf é válido.
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !CpfCnpj.isValidCpf(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		// Verifica se é pessoa jurídica e se o cnpj é válido.
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !CpfCnpj.isValidCnpj(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFildName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
