package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
                deviceSource.getDeviceModel().getName() + "-" +
                deviceSource.getManufactureDate() + "-" +
                deviceSource.getDeviceModel().getSerialNumber() + "-";

    }
}
