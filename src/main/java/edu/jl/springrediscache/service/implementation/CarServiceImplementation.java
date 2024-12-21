package edu.jl.springrediscache.service.implementation;

import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.exception.ResourceNotFoundException;
import edu.jl.springrediscache.mapper.Mapper;
import edu.jl.springrediscache.model.CarModel;
import edu.jl.springrediscache.repository.CarRepository;
import edu.jl.springrediscache.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImplementation implements CarService {
    private final CarRepository carRepository;
    private final Mapper mapper;

    @Autowired
    public CarServiceImplementation(CarRepository carRepository, Mapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "cars", key = "#id")
    public CarResponseDTO findByIdWithCacheSupport(Long id) throws ResourceNotFoundException {
        //System.out.printf("Searching for car with id: %s.%n", id);
        CarModel carFound = carRepository.findCarById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return mapper.convertToObject(carFound, CarResponseDTO.class);
    }

    @Override
    public CarResponseDTO findByIdWithoutCacheSupport(Long id) throws ResourceNotFoundException {
        //System.out.printf("Searching for car with id: %s.%n", id);
        CarModel carFound = carRepository.findCarById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return mapper.convertToObject(carFound, CarResponseDTO.class);
    }

    @Override
    @CacheEvict(value = "cars", key = "#id")
    public void deleteById(Long id) {
        carRepository.deleteCarById(id);
    }
}
