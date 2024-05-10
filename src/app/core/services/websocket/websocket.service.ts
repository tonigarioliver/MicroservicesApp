import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { WebSocketMessage } from 'src/app/core/models/web-socket-message';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private readonly url: string = 'ws://localhost:8005/mqtt'
  private socket: WebSocket | null = null;
  messageReceived: Subject<string> = new Subject<string>();
  connected: Subject<boolean> = new Subject<boolean>();

  constructor() { }

  public connect(): void {
    this.socket = new WebSocket(this.url);

    this.socket.onopen = () => {
      console.log('WebSocket connection established.');
      this.connected.next(true);
    };

    this.socket.onmessage = (event) => {
      const message = event.data;
      console.log('Received message:', message);
      this.messageReceived.next(message);
    };

    this.socket.onclose = (event) => {
      console.log('WebSocket connection closed:', event);
      this.connected.next(false);
    };

    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }

  sendMessage(webSocketMessage: WebSocketMessage): void {
    this.socket?.send(JSON.stringify(webSocketMessage));
  }

  closeConnection(): void {
    this.socket?.close();
  }
}
