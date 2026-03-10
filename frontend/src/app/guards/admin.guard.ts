import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  canActivate(): boolean {

  if (!this.authService.isLoggedIn()) {
    this.router.navigate(['/login']);
    return false;
  }

  const role = this.authService.getUserRole();

  if (role === 'ADMIN') {
    return true;
  }

  this.router.navigate(['/dashboard']);
  return false;
}
}