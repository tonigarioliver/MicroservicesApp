import { Injectable } from '@angular/core';
import { RealTimeWebSocketRequest } from 'src/app/core/models/real-time-iot-message';
import { WebSocketMessage } from 'src/app/core/models/web-socket-message';
import { WebsocketService } from 'src/app/core/services/websocket/websocket.service';

@Injectable({
  providedIn: 'root'
})
export class RealTimeIotService extends WebsocketService {

  constructor() {
    super();
  }
  public override connect(): void {
    super.connect()
  }
  public sendIOTRequest(req: RealTimeWebSocketRequest): void {
    const message: WebSocketMessage = {
      command: "SUBSCRIBE",
      payload: JSON.stringify(req)
    }
    this.sendMessage(message)
  }
}
