package edu.jl.springrediscache.controller;

import edu.jl.springrediscache.dto.car.CarRequestDTO;
import edu.jl.springrediscache.dto.car.CarResponseDTO;
import edu.jl.springrediscache.dto.excepiton.ExceptionDTO;
import edu.jl.springrediscache.service.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/cars")
@Tag(name = "Car Controller", description = "Manage car-related operations")
public class CarController extends BadRequestValidationHelper {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Retrieve a car by ID with caching support",
            description = "Fetches the details of a car by its ID with caching enabled for optimized performance.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @GetMapping("/cacheable/{id}")
    public ResponseEntity<CarResponseDTO> findByIdWithCacheSupport(
            @Parameter(description = "ID of the car to be retrieved", required = true) @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(carService.findByIdWithCacheSupport(id));
    }

    @Operation(summary = "Retrieve a car by ID without caching",
            description = "Fetches the details of a car by its ID without using caching mechanisms.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> findByIdWithoutCacheSupport(
            @Parameter(description = "ID of the car to be retrieved", required = true) @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(carService.findByIdWithoutCacheSupport(id));
    }

    @Operation(summary = "Delete a car by ID",
            description = "Deletes a car identified by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID of the car to be deleted", required = true) @PathVariable("id") Long id) {
        carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update a car by ID",
            description = "Updates the details of an existing car identified by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> updateById(
            @Parameter(description = "ID of the car to be updated", required = true) @PathVariable("id") Long id,
            @Valid @RequestBody CarRequestDTO update,
            BindingResult bindingResult) throws BadRequestException {
        validateRequestErrors(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(carService.updateById(id, update));
    }

    @Operation(summary = "Search cars by model",
            description = "Retrieves a paginated list of cars containing the specified model name, ignoring case.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<CarResponseDTO>> findByModelContainingIgnoreCase(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, direction = Sort.Direction.ASC, sort = {"model"}) Pageable pageable,
            @Parameter(description = "Partial or full model name to search for")
            @RequestParam(name = "model", defaultValue = "") String name) {
        return ResponseEntity.ok(carService.findByModelContainingIgnoreCase(name, pageable));
    }

    @Operation(summary = "Create a new car",
            description = "Adds a new car to the system with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Car created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
    })
    @PostMapping
    public ResponseEntity<CarResponseDTO> save(
            @Valid @RequestBody CarRequestDTO newCar,
            BindingResult bindingResult) throws BadRequestException {
        validateRequestErrors(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(newCar));
    }
}
