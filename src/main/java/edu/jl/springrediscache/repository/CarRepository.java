package edu.jl.springrediscache.repository;

import edu.jl.springrediscache.model.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarModel, Long> {
    Page<CarModel> findByModelContainingIgnoreCase(String model, Pageable pageable);
}
