package com.antonigari.iotdeviceservice.controller;


import com.antonigari.iotdeviceservice.model.DeviceMeasurementDetailsDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementsDto;
import com.antonigari.iotdeviceservice.service.impl.DeviceMeasurementManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/device-measurements")
@Tag(name = "DeviceMeasurement Controller", description = "Operations related to DevicesMeasurements")
@AllArgsConstructor
public class DeviceMeasurementController {
    private final DeviceMeasurementManagerService deviceMeasurementManagerService;

    @PostMapping
    @Operation(summary = "Create DeviceMeasurement", description = "Create a new DeviceMeasurement.")
    public ResponseEntity<DeviceMeasurementDto> createDeviceMeasurement(
            @RequestBody @NotNull final DeviceMeasurementRequestDto request
    ) {
        return new ResponseEntity<>(this.deviceMeasurementManagerService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Read DeviceMeasurement", description = "Read list of DeviceMeasurement.")
    public ResponseEntity<DeviceMeasurementsDto> getDeviceMeasurements() {
        return new ResponseEntity<>(this.deviceMeasurementManagerService.getAllAsync().join(), HttpStatus.OK);
    }

    @GetMapping(value = "/{topic}")
    @Operation(summary = "Get DeviceMeasurement", description = "Get DeviceMeasurement by topic.")
    public ResponseEntity<DeviceMeasurementDto> getDeviceMeasurementByTopic(
            @PathVariable @NotEmpty final String topic
    ) {
        return new ResponseEntity<>(this.deviceMeasurementManagerService.getAsyncByTopic(topic).join(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/details")
    @Operation(summary = "Get DeviceMeasurement details", description = "Get DeviceMeasurement details by id.")
    public ResponseEntity<DeviceMeasurementDetailsDto> getDeviceMeasurementDetailsById(
            @PathVariable @Positive final long id
    ) {
        return new ResponseEntity<>(this.deviceMeasurementManagerService.getDetailsAsyncById(id).join(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DeviceMeasurement", description = "Update existing DeviceMeasurement.")
    public ResponseEntity<DeviceMeasurementDto> updateDevice(
            @PathVariable @Positive final long id,
            @RequestBody @NotNull final DeviceMeasurementRequestDto requestDto
    ) {
        return new ResponseEntity<>(this.deviceMeasurementManagerService.update(id, requestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DeviceMeasurement", description = "Delete DeviceMeasurement by id.")
    public ResponseEntity<Void> deleteDeviceMeasurement(@PathVariable @Positive final long id) {
        this.deviceMeasurementManagerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
