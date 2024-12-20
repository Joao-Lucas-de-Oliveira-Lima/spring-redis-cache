package edu.jl.springrediscache.dto.excepiton;

import java.util.Date;

public record ExceptionDTO (
        Date timestamp,
        String message,
        String details) {
}
