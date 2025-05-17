package com.antonigari.iotdeviceservice.configuration.db;

import com.antonigari.iotdeviceservice.data.model.MeasurementType;
import com.antonigari.iotdeviceservice.data.model.MeasurementTypeName;
import com.antonigari.iotdeviceservice.data.repository.MeasurementTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final MeasurementTypeRepository measurementTypeRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        this.initializeMeasurementTypes();
    }

    private void initializeMeasurementTypes() {
        if (this.measurementTypeRepository.count() == 0) {
            this.measurementTypeRepository.saveAll(List.of(
                    MeasurementType.builder().typeName(MeasurementTypeName.STRING).build(),
                    MeasurementType.builder().typeName(MeasurementTypeName.BOOLEAN).build(),
                    MeasurementType.builder().typeName(MeasurementTypeName.NUMERIC).build()
            ));
        }
    }
}

