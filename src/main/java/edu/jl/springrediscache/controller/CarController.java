package edu.jl.springrediscache.controller;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cacheable/{id}")
    public ResponseEntity<CarResponseDTO> findByIdWithCacheSupport(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(carService.findByIdWithCacheSupport(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> findByIdWithoutCacheSupport(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(carService.findByIdWithoutCacheSupport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        carService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> updateById(
            @PathVariable("id") Long id, @RequestBody CarRequestDTO update){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.updateById(id, update));
    }
}
