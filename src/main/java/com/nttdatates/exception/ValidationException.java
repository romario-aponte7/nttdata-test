package com.nttdatates.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "Ocurrio un error")
public class ValidationException extends ResponseStatusException {

    public ValidationException(String exString) {
        super(HttpStatus.PRECONDITION_FAILED, exString);
    }

    public ValidationException(String exString, Set<ConstraintViolation<Object>> constraintViolations) {
        super(HttpStatus.PRECONDITION_FAILED, createMessage(exString, constraintViolations));
    }

    private static String createMessage(String exString, Set<ConstraintViolation<Object>> constraintViolations) {
        if (constraintViolations == null)
            return "";

        return String.format("Datos incorrectos: %s",
                constraintViolations.stream()
                        .map(ValidationException::buildFieldFailed)
                        .collect(Collectors.joining(", "))
        );
    }

    private static String buildFieldFailed(ConstraintViolation constraintViolation) {
        return String.format("Campo= %s, Mensaje= %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
    }

}