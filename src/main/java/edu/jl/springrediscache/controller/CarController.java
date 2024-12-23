package edu.jl.springrediscache.controller;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.service.CarService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController extends BadRequestValidationHelper {
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
            @PathVariable("id") Long id,
            @Valid @RequestBody CarRequestDTO update,
            BindingResult bindingResult) throws BadRequestException {
        validateRequestErrors(bindingResult);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.updateById(id, update));
    }

    @GetMapping
    public ResponseEntity<Page<CarResponseDTO>> findByModelContainingIgnoreCase(
            @PageableDefault(direction = Sort.Direction.ASC, sort = {"model"}) Pageable pageable,
            @RequestParam(name = "model", defaultValue = "") String name) {
        return ResponseEntity.ok(carService.findByModelContainingIgnoreCase(name, pageable));
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> save(
            @Valid @RequestBody CarRequestDTO newCar,
            BindingResult bindingResult) throws BadRequestException{
        validateRequestErrors(bindingResult);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carService.save(newCar));
    }
}
