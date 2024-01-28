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
public class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceModelId;

    @Column(unique = true)
    private String serialNumber;

    private String name;

    @ManyToOne
    @JoinColumn(name = "deviceFunctionalityId")
    private DeviceFunctionality deviceFunctionality;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @OneToMany(mappedBy = "deviceModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;
}
