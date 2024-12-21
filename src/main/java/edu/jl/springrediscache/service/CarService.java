package edu.jl.springrediscache.service;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;

import java.net.URI;


public interface CarService {
    CarResponseDTO findByIdWithCacheSupport(Long id);
    CarResponseDTO findByIdWithoutCacheSupport(Long id);
    void deleteById(Long id);
    CarResponseDTO updateById(Long id, CarRequestDTO update);
    CarResponseDTO save(CarRequestDTO newCar);
}
