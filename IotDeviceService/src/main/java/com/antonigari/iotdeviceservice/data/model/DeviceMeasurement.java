package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "physicalMeasurementId", nullable = false)
    private PhysicalMeasurement physicalMeasurement;

    @Column(unique = true)
    private String iotTopic;
}
