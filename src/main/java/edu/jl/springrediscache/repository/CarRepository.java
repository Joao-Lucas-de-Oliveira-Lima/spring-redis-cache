package edu.jl.springrediscache.repository;

import edu.jl.springrediscache.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarModel, Long> {
    @Query("SELECT car FROM CarModel car WHERE car.id = :id")
    Optional<CarModel> findCarById(@Param("id") long id);

    @Query("DELETE FROM CarModel car WHERE car.id = :id")
    void deleteCarById(@Param("id") long id);
}
