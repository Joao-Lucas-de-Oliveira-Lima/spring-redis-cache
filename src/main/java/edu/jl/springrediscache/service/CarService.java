package edu.jl.springrediscache.service;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CarService {
    CarResponseDTO findByIdWithCacheSupport(Long id);

    CarResponseDTO findByIdWithoutCacheSupport(Long id);

    void deleteById(Long id);

    CarResponseDTO updateById(Long id, CarRequestDTO update);

    CarResponseDTO save(CarRequestDTO newCar);

    Page<CarResponseDTO> findByModelContainingIgnoreCase(String name, Pageable pageable);
}
