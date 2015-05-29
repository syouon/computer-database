package com.excilys.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom annotation for LocalDate validation with ComputerDTOValidator class.
 * reference:
 * http://codetutr.com/2013/05/29/custom-spring-mvc-validation-annotations
 * 
 * @see com.excilys.util.ComputerDTOValidator
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ComputerDTOValidator.class)
public @interface LocalDateFormat {

	String message() default "{date_error}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
