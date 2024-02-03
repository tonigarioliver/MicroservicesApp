package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long physicalMeasurementId;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "measurementTypeId")
    private MeasurementType measurementType;

    private String unit;

    @OneToMany(mappedBy = "physicalMeasurement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceMeasurement> deviceMeasurements;
}
