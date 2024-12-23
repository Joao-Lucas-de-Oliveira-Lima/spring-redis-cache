package edu.jl.springrediscache.service.implementation;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.exception.ResourceNotFoundException;
import edu.jl.springrediscache.mapper.Mapper;
import edu.jl.springrediscache.model.CarModel;
import edu.jl.springrediscache.repository.CarRepository;
import edu.jl.springrediscache.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO: Improve cache behavior
/*
    Ensure that outdated entries in the 'carPage' cache are properly removed or updated.
    This should account for changes caused by delete, create, or update operations in the 'car' cache
    that may impact the data consistency.
*/

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
    @Cacheable(value = "car", key = "#id")
    public CarResponseDTO findByIdWithCacheSupport(Long id) throws ResourceNotFoundException {
        //System.out.printf("Searching for car with id: %s.%n", id);
        CarModel carFound = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Car with id %s not found!", id)));
        return mapper.mapProperties(carFound, CarResponseDTO.class);
    }

    @Override
    public CarResponseDTO findByIdWithoutCacheSupport(Long id) throws ResourceNotFoundException {
        //System.out.printf("Searching for car with id: %s.%n", id);
        CarModel carFound = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Car with id %s not found!", id)));
        return mapper.mapProperties(carFound, CarResponseDTO.class);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "carPage", allEntries = true),
            @CacheEvict(value = "car", key = "#id")
    })
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(
            evict = @CacheEvict(value = "carPage", allEntries = true),
            put = @CachePut(value = "car", key = "#id")
    )
    public CarResponseDTO updateById(Long id, CarRequestDTO update) throws ResourceNotFoundException {
        CarModel carToBeUpdated = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Car with id %s not found!", id)));
        mapper.mapProperties(update, carToBeUpdated);
        CarModel updatedCar = carRepository.save(carToBeUpdated);
        return mapper.mapProperties(updatedCar, CarResponseDTO.class);
    }

    @Override
    @Caching(
            put = @CachePut(value = "car", key = "#result.id"),
            evict = @CacheEvict(value = "carPage", allEntries = true)
    )
    public CarResponseDTO save(CarRequestDTO newCar) {
        CarModel carSaved = carRepository.save(mapper.mapProperties(newCar, CarModel.class));
        return mapper.mapProperties(carSaved, CarResponseDTO.class);
    }
  
    @Override
    @Cacheable(value = "carPage")
    public Page<CarResponseDTO> findByModelContainingIgnoreCase(String name, Pageable pageable) {
        Page<CarModel> carModelPage = carRepository.findByModelContainingIgnoreCase(name, pageable);
        return carModelPage.map(carModel -> mapper.mapProperties(carModel, CarResponseDTO.class));
    }
}
