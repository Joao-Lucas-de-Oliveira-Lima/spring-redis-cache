package edu.jl.springrediscache.dto.car;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class CarRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String make;
    private String model;
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