package com.neonlab.common.utilities;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


import java.util.stream.Collectors;



@Component
public class ValidationUtils {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    // validate any object type
    public <T> void validate(T object) {
        var violations = validator.validate(object);
        if (!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    public <T> void validate(T object, Class<?> group) {
        var violations = validator.validate(object, group);
        if (!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    private static String formatErrors(Errors errors) {
        return errors.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }
}
