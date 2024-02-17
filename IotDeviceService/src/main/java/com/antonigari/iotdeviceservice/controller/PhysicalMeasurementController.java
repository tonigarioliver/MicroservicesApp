package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.*;
import com.antonigari.iotdeviceservice.service.impl.PhysicalMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "/api/v1/physical-measurements")
@Tag(name = "PhysicalMeasurement Controller", description = "Operations related to Devices")
@AllArgsConstructor
public class PhysicalMeasurementController {
    private final PhysicalMeasurementService physicalMeasurementService;
    @PostMapping
    @Operation(summary = "Create PhysicalMeasurement", description = "Create a new PhysicalMeasurement.")
    public ResponseEntity<PhysicalMeasurementDto> createPhysicalMeasurement
            (@RequestBody @NotNull final PhysicalMeasurementRequestDto physicalMeasurementRequestDto
            ) {
        return new ResponseEntity<>(this.physicalMeasurementService.create(physicalMeasurementRequestDto),
                HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(summary = "Read PhysicalMeasurements", description = "Read list of PhysicalMeasurements.")
    public ResponseEntity<PhysicalMeasurementsDto> getPhysicalMeasurements() {
        return new ResponseEntity<>(this.physicalMeasurementService.getAllAsync().join(), HttpStatus.OK);
    }
    @GetMapping(value = "/{name}")
    @Operation(summary = "Get PhysicalMeasurement", description = "Get PhysicalMeasurement by name.")
    public ResponseEntity<PhysicalMeasurementDto> getPhysicalMeasurementByName(@PathVariable @NotEmpty final String name) {
        return new ResponseEntity<>(this.physicalMeasurementService.findByName(name).join(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update PhysicalMeasurement", description = "Update existing PhysicalMeasurement.")
    public ResponseEntity<PhysicalMeasurementDto> updatePhysicalMeasurement(
            @PathVariable @Positive final long id,
            @RequestBody @NotNull final PhysicalMeasurementRequestDto physicalMeasurementRequestDto)
    {
        return new ResponseEntity<>(this.physicalMeasurementService.update(id,physicalMeasurementRequestDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete PhysicalMeasurement", description = "Delete PhysicalMeasurement by id.")
    public ResponseEntity<Void> deletePhysicalMeasurement(@PathVariable @Positive final long id) {
        this.physicalMeasurementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}