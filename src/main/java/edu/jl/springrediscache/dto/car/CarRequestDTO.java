package edu.jl.springrediscache.dto.car;

import edu.jl.springrediscache.validation.constraint.ValidYearOfRelease;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CarRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "The make field must not be blank. Please provide a valid car make.")
    private String make;
    @NotBlank(message = "The model field must not be blank. Please provide a valid car model.")
    private String model;
    @ValidYearOfRelease(message = "The year of release must be between 1990 and the current year. Please provide a valid year.")
    private Short yearOfRelease;

    public CarRequestDTO(String make, String model, Short yearOfRelease) {
        this.make = make;
        this.model = model;
        this.yearOfRelease = yearOfRelease;
    }
}