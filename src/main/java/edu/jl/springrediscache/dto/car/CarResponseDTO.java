package edu.jl.springrediscache.dto.car;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class CarResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String make;
    private String model;
    private Short yearOfRelease;

    public CarResponseDTO(Long id, String make, String model, Short yearOfRelease) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.yearOfRelease = yearOfRelease;
    }

    public CarResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        CarResponseDTO that = (CarResponseDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(yearOfRelease, that.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, yearOfRelease);
    }

    @Override
    public String toString() {
        return "CarResponseDTO{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }
}
