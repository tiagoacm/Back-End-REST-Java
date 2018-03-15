package br.com.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import br.com.loja.model.ValidationError;

public class ValidationErrorBuilder {

	public static ValidationError fromBindingErrors(Errors errors) {
		ValidationError error = new ValidationError("Dados inconsistentes. " + errors.getErrorCount() + " erro(s)");
		for (ObjectError objectError : errors.getAllErrors()) {
			error.addValidationError(objectError.getDefaultMessage());
		}
		return error;
	}
}
