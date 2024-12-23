package edu.jl.springrediscache.dto.excepiton;

import java.time.Instant;

public record ExceptionDTO(
        Instant timestamp,
        String message,
        String details) {
}
