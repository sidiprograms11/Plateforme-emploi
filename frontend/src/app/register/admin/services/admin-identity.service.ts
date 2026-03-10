import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminIdentityService {

  private apiUrl = 'http://localhost:8080/api/admin/identity';

  constructor(private http: HttpClient) {}

  getPending(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pending`);
  }

  approve(userId: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/${userId}/approve`,
      {},
      {
        responseType: 'text' // ⭐⭐⭐ OBLIGATOIRE
      }
    );
  }

  reject(userId: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/${userId}/reject`,
      {},
      {
        responseType: 'text' // ⭐⭐⭐ OBLIGATOIRE
      }
    );
  }
}