package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/device")
@Tag(name = "Device Controller", description = "Operations related to Devices")
@AllArgsConstructor
public class DeviceController {
    @PostMapping
    @Operation(summary = "Create Device", description = "Create a new Device.")
    public ResponseEntity<DeviceDto> createDevice() {
        throw notImplementedException();
    }
    @GetMapping
    @Operation(summary = "Read Devices", description = "Read list of Devices.")
    public ResponseEntity<DevicesDto> readDevices() {
        throw notImplementedException();
    }

    @PutMapping
    @Operation(summary = "Update Device", description = "Update existing Device.")
    public ResponseEntity<DeviceDto> updateDevice() {
        throw notImplementedException();
    }

    @DeleteMapping
    @Operation(summary = "Delete Device", description = "Delete Device by id.")
    public ResponseEntity<Void> deleteDevice() {
        throw notImplementedException();
    }

    private UnsupportedOperationException notImplementedException() {
        return new UnsupportedOperationException("Not implemented yet.");
    }
}
