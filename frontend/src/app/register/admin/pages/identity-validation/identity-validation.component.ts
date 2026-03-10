import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminIdentityService } from '../../services/admin-identity.service';

@Component({
  selector: 'app-identity-validation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './identity-validation.component.html',
  styleUrls: ['./identity-validation.component.scss']
})
export class IdentityValidationComponent implements OnInit {

  identities: any[] = [];
  loading = true;

  constructor(private adminService: AdminIdentityService) {}

  ngOnInit(): void {
    this.loadPending();
  }

  loadPending() {
    this.adminService.getPending().subscribe({
      next: data => {
        console.log('Données reçues :', data);
        this.identities = data;
        this.loading = false;
      },
      error: err => {
        console.error('Erreur API :', err);
        this.loading = false;
      }
    });
  }

  approve(userId: string) {
    this.adminService.approve(userId).subscribe({
      next: () => {
        this.identities = this.identities.filter(u => u.id !== userId);
      },
      error: err => {
        console.error('Erreur approve:', err);
      }
    });
  }

  reject(userId: string) {
    this.adminService.reject(userId).subscribe({
      next: () => {
        this.identities = this.identities.filter(u => u.id !== userId);
      },
      error: err => {
        console.error('Erreur reject:', err);
      }
    });
  }
}