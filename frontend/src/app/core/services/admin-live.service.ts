import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class AdminLiveService {

  private stompClient: any;

  connect(callback: (msg: string) => void) {

    const socket = new SockJS('http://localhost:8080/admin-live');

    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {

      this.stompClient.subscribe('/topic/admin-events', (event: any) => {

        callback(event.body);

      });

    });

  }

}