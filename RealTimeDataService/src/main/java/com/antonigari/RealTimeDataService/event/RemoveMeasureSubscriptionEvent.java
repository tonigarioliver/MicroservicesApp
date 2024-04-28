package com.antonigari.RealTimeDataService.event;

import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import lombok.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@Value
public class RemoveMeasureSubscriptionEvent extends ApplicationEvent {
    private DeviceMeasurementDto measurement;
    private WebSocketSession session;

    public RemoveMeasureSubscriptionEvent(final Object source, final DeviceMeasurementDto measurementDto, final WebSocketSession session) {
        super(source);
        this.measurement = measurementDto;
        this.session = session;
    }
}