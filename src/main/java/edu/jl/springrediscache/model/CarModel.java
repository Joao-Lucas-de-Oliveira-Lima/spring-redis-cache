package edu.jl.springrediscache.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "car")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;
    private String make;
    private String model;
    @Column(name = "year_of_release")
    private Short yearOfRelease;

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
        CarModel carModel = (CarModel) object;
        return Objects.equals(id, carModel.id) && Objects.equals(make, carModel.make) && Objects.equals(model, carModel.model) && Objects.equals(yearOfRelease, carModel.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, yearOfRelease);
    }
}
