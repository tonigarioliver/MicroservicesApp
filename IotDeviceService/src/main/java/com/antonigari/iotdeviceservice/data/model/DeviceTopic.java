package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeviceTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String topic;
    private String deviceManufactureCode;
    @OneToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    static public String getTopic(final Device deviceSource) {
        return deviceSource.getManufactureCode() + "-" +
                deviceSource.getDeviceModel().getSerialNumber() + "-" +
                deviceSource.getDeviceModel().getName() + "-" +
                deviceSource.getDeviceModel().getDeviceFunctionality().getName();
    }
}
