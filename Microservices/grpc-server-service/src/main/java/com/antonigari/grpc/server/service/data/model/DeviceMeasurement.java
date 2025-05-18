package com.antonigari.grpc.server.service.data.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceMeasurementId;

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    @Column(unique = true)
    private String name;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "measurementTypeId")
    private MeasurementType measurementType;

    @Column(unique = true)
    private String topic;

    @OneToMany(mappedBy = "deviceMeasurement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Singular
    List<DeviceMeasurementPayload> deviceMeasurementPayloads;

    static public String getTopic(final Device deviceSource, final String measurementName) {
        return "Device ManufactureCode: " + deviceSource.getManufactureCode() + "-" +
                "DeviceModel SerialNumber: " + deviceSource.getDeviceModel().getSerialNumber() + "-" +
                "Measure name: " + measurementName;

    }
}
