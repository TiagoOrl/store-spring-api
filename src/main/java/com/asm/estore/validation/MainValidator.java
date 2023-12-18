package com.asm.estore.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Component
public class MainValidator {
    @Autowired
    private Validator validator;
    public <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream().map(
                    i -> i.getMessage()
            ).toList();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                errors.stream().reduce("Validation Errors: ", (i, j) -> i + ", " + j)
            );
        }
    }
}
