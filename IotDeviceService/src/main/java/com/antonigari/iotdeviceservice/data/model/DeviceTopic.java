package com.antonigari.iotdeviceservice.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class DeviceTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serialNumber;
    private String name;
    private String topic;
    public DeviceTopic(String name, String serialNumber) {
        this.name = name;
        this.serialNumber=serialNumber;
        this.topic = serialNumber.concat(name);
    }
}

