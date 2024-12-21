package edu.jl.springrediscache.service;

import edu.jl.springrediscache.dto.car.CarResponseDTO;


public interface CarService {
    CarResponseDTO findByIdWithCacheSupport(Long id);
    CarResponseDTO findByIdWithoutCacheSupport(Long id);
    void deleteById(Long id);
}
