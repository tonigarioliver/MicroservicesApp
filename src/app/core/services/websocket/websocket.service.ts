import { Injectable } from '@angular/core';
import { webSocket } from 'rxjs/webSocket';
import { WebSocketMessage } from 'src/app/core/models/web-socket-message';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private readonly url: string = 'ws://localhost:8005/mqtt'
  public webSocketSubject;
  public webSocket$;

  constructor() {
    this.webSocketSubject = webSocket(this.url);
    this.webSocket$ = this.webSocketSubject.asObservable();
  }

  public sendMessage(message: WebSocketMessage): void {
    this.webSocketSubject.next(JSON.stringify(message));
  }
}
