// src/app/services/api.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private base = `${environment.apiBase}/api`;

  constructor(private http: HttpClient) {}

  getCandidates() {
    return this.http.get(`${this.base}/candidates`);
  }
}
