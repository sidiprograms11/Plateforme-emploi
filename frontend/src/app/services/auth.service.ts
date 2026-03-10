import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  id: string;
  email: string;
  role: string;
  status: string;
  token: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  birthDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api';
  private tokenKey = 'token';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  // =========================
  // AUTH API CALLS
  // =========================

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/auth/login`, request)
      .pipe(
        tap(response => {
          this.saveToken(response.token);
        })
      );
  }

  register(request: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, request);
  }

  // =========================
  // TOKEN STORAGE
  // =========================

  saveToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  // =========================
  // AUTH STATE
  // =========================

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;

    const decoded = this.decodeToken();
    if (!decoded) return false;

    const expiration = decoded.exp * 1000;
    return Date.now() < expiration;
  }

  // =========================
  // JWT DECODING
  // =========================

  private decodeToken(): any {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch {
      return null;
    }
  }

  getCurrentUser() {
    return this.decodeToken();
  }

  getUserId(): string {
    return this.decodeToken()?.sub || '';
  }

  getUserEmail(): string {
    return this.decodeToken()?.email || '';
  }

  getUserRole(): string {
    return this.decodeToken()?.role || '';
  }

  getUserStatus(): string {
    return this.decodeToken()?.status || '';
  }
}