package com.example.projetoSpring.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.UsuarioDto;
import com.example.projetoSpring.repositories.UsuarioRepository;
import com.example.projetoSpring.resources.exception.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsuarioValidator implements ConstraintValidator<UsuarioValidacoes, UsuarioDto> {

	@Autowired
	private UsuarioRepository re;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(UsuarioValidacoes ann) {
		
	}

	@Override
	public boolean isValid(UsuarioDto value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*@Override
	public boolean isValid(UsuarioDto objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Integer uriId = 0;
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if(!map.isEmpty()) {
			uriId = Integer.parseInt(map.get("id"));
		}
		
		Usuario aux = re.findByEmail(objDto.getEmail());
		if((aux != null && map.isEmpty())) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		if(!map.isEmpty()) {
			Usuario auxUpdate = re.findByEmailId(uriId, objDto.getEmail());
			if(auxUpdate != null && !objDto.getEmail().equalsIgnoreCase(auxUpdate.getEmail())) {
				list.add(new FieldMessage("email", "Email já existe"));
			}
		}
		
		for (FieldMessage r : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(r.getMessage()).addPropertyNode(r.getFieldName())
					.addConstraintViolation();
		}
		
		
		return list.isEmpty();
		
	}*/
	
}
