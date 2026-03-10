import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminIdentityService {

  private api = "http://localhost:8080/api/admin/identity";

  constructor(private http: HttpClient) {}

  getPending(): Observable<any[]> {
    return this.http.get<any[]>(`${this.api}/pending`);
  }

  approve(userId: number): Observable<any> {
    return this.http.post(`${this.api}/${userId}/approve`, {});
  }

  reject(userId: number): Observable<any> {
    return this.http.post(`${this.api}/${userId}/reject`, {});
  }

}