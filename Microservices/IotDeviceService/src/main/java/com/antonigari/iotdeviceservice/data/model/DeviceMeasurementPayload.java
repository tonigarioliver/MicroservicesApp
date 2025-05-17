package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMeasurementPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceMeasurementPayloadId;
    @ManyToOne
    @JoinColumn(name = "deviceMeasurementId")
    private DeviceMeasurement deviceMeasurement;
    private String stringValue;
    private Float numValue;
    private Boolean booleanValue;
    
}
