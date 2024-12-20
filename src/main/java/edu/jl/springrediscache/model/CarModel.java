package edu.jl.springrediscache.model;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@Entity
@Table(name = "car")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;
    private String make;
    private String model;
    @Column(name = "year_of_release")
    private short yearOfRelease;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public short getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(short yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CarModel carModel = (CarModel) object;
        return id == carModel.id && yearOfRelease == carModel.yearOfRelease && Objects.equals(make, carModel.make) && Objects.equals(model, carModel.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, yearOfRelease);
    }
}
