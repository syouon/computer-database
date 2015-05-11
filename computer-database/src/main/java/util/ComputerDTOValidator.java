package util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ComputerDTOValidator implements
		ConstraintValidator<LocalDateFormat, String> {

	@Override
	public void initialize(LocalDateFormat arg0) {
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return arg0 != null && Utils.isWellFormedDate(arg0);
	}

}
