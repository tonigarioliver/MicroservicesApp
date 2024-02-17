package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.service.impl.DeviceService;
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
@RequestMapping(path = "/api/v1/devices")
@Tag(name = "Device Controller", description = "Operations related to Devices")
@AllArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    @PostMapping
    @Operation(summary = "Create Device", description = "Create a new Device.")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody @NotNull final DeviceRequestDto deviceRequestDto) {
        return new ResponseEntity<>(this.deviceService.create(deviceRequestDto), HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(summary = "Read Devices", description = "Read list of Devices.")
    public ResponseEntity<DevicesDto> getDevices() {
        return new ResponseEntity<>(this.deviceService.getAllAsync().join(), HttpStatus.OK);
    }
    @GetMapping(value = "/{manufactureCode}")
    @Operation(summary = "Get Device", description = "Get device by Serial Number.")
    public ResponseEntity<DeviceDto> getDeviceBySerilaNumber(@PathVariable @NotEmpty final String manufactureCode) {
        return new ResponseEntity<>(this.deviceService.getAsyncByManufactureCode(manufactureCode).join(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Device", description = "Update existing Device.")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable @Positive final long id,
                                                  @RequestBody @NotNull final DeviceRequestDto deviceRequestDto) {
        return new ResponseEntity<>(this.deviceService.update(id,deviceRequestDto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Device", description = "Delete Device by id.")
    public ResponseEntity<Void> deleteDevice(@PathVariable @Positive final long id) {
        this.deviceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
