package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.MeasurementTypesDto;
import com.antonigari.iotdeviceservice.service.IMeasurementTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/device-measurement-type")
@Tag(name = "DeviceMeasurementType Controller", description = "Operations deviceMeasurementTypes")
@AllArgsConstructor
public class MeasurementTypeController {
    private final IMeasurementTypeService measurementTypeService;

    @GetMapping
    @Operation(summary = "Read MeasurementTYpe", description = "Read list of MeasurementType.")
    public ResponseEntity<MeasurementTypesDto> getMeasurementTypes() {
        return new ResponseEntity<>(this.measurementTypeService.getAllAsync().join(), HttpStatus.OK);
    }
}
