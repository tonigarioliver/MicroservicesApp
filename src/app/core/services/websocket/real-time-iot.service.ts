import { Injectable } from '@angular/core';
import { RealTimeWebSocketRequest } from 'src/app/core/models/real-time-iot-message';
import { WebSocketMessage } from 'src/app/core/models/web-socket-message';
import { WebSocketService } from 'src/app/core/services/websocket/websocket.service';

@Injectable({
  providedIn: 'root'
})
export class RealTimeIotService extends WebSocketService {

  constructor() {
    super();
  }

  public sendIOTRequest(req: RealTimeWebSocketRequest): void {
    const message: WebSocketMessage = {
      command: req.command,
      payload: req.deviceMeasurement.topic
    }
    this.sendMessage(message)
  }
}
