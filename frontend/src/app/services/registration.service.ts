import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistrationFullRequest } from './models/RegistrationFullRequest';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private readonly API_URL = 'http://localhost:8080/api/register';

  constructor(private http: HttpClient) {}

  register(request: RegistrationFullRequest) {
    return this.http.post(this.API_URL, request);
  }
}
