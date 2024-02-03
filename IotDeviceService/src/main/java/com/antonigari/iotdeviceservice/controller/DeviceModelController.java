package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import com.antonigari.iotdeviceservice.model.NewDeviceModelRequestDto;
import com.antonigari.iotdeviceservice.model.UpdateDeviceModelRequestDto;
import com.antonigari.iotdeviceservice.service.impl.DeviceModelService;
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
@RequestMapping(path = "/api/v1/device-model")
@Tag(name = "DeviceModel Controller", description = "Operations related to Device Models")
@AllArgsConstructor
public class DeviceModelController {
    private final DeviceModelService deviceModelService;

    @PostMapping
    @Operation(summary = "Create DeviceModel", description = "Create a new DeviceModel.")
    public ResponseEntity<DeviceModelDto> createDeviceModel(@RequestBody @NotNull final NewDeviceModelRequestDto newDeviceModelRequestDto) {
        return new ResponseEntity<>(this.deviceModelService.create(newDeviceModelRequestDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Read DeviceModels", description = "Read list of DeviceModels.")
    public ResponseEntity<DeviceModelsDto> getDeviceModels() {
        return new ResponseEntity<>(this.deviceModelService.getAllAsync().join(), HttpStatus.OK);
    }

    @GetMapping(value = "/{serialNumber}")
    @Operation(summary = "Get DeviceModel", description = "Get DeviceModel by name.")
    public ResponseEntity<DeviceModelDto> getDeviceModelByName(@PathVariable @NotEmpty final String serialNumber) {
        return new ResponseEntity<>(this.deviceModelService.getAsyncBySerialNumber(serialNumber).join(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update DeviceModel", description = "Update existing DeviceModel.")
    public ResponseEntity<DeviceModelDto> updateDeviceModel(
            @PathVariable @Positive final Long id,
            @RequestBody @NotNull final UpdateDeviceModelRequestDto updateDeviceModelRequestDto) {
        return new ResponseEntity<>(this.deviceModelService.update(id, updateDeviceModelRequestDto),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete DeviceModel", description = "Delete DeviceModel by id.")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable @Positive final Long id) {
        this.deviceModelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
