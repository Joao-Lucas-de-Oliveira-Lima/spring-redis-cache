package edu.jl.springrediscache.dto.car;

import edu.jl.springrediscache.validation.constraint.ValidYearOfRelease;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    public CarRequestDTO() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Short getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Short yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CarRequestDTO that = (CarRequestDTO) object;
        return Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(yearOfRelease, that.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, yearOfRelease);
    }
}