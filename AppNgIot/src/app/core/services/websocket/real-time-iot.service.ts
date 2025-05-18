import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { DeviceMeasurementPayload } from 'src/app/core/models/device-measurement-payload';
import { RealTimeWebSocketRequest } from 'src/app/core/models/real-time-iot-message';
import { WebSocketMessage } from 'src/app/core/models/web-socket-message';
import { WebsocketService } from 'src/app/core/services/websocket/websocket.service';

@Injectable({
  providedIn: 'root'
})
export class RealTimeIotService extends WebsocketService {
  private messageReceivedSubject = new Subject<DeviceMeasurementPayload>();
  public listenMeasuresPayload: Observable<DeviceMeasurementPayload> = this.messageReceivedSubject.asObservable();
  constructor() {
    super();
  }
  public override connect(): void {
    super.connect()
    this.messageReceived.subscribe((message: string) => {
      const payload: DeviceMeasurementPayload = JSON.parse(message);
      this.messageReceivedSubject.next(payload);
    });
  }

  public sendSubscribeIOTRequest(req: RealTimeWebSocketRequest): void {
    const message: WebSocketMessage = {
      command: "SUBSCRIBE",
      payload: JSON.stringify(req)
    }
    this.sendMessage(message)
  }
  public sendUnsusbscribeIOTRequest(req: RealTimeWebSocketRequest): void {
    const message: WebSocketMessage = {
      command: "REMOVE",
      payload: JSON.stringify(req)
    }
    this.sendMessage(message)
  }
}
