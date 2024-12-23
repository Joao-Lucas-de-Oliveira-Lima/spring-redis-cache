package edu.jl.springrediscache.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public abstract class BadRequestValidationHelper {

    protected void validateRequestErrors(BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(" - ", "Validation errors: ", ""));
            throw new BadRequestException(errorMessage);
        }
    }
}