import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-offers',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="header">
      <h1>💼 Gestion des Offres</h1>

      <button class="create-btn" (click)="goToCreate()">
        ➕ Créer une offre
      </button>
    </div>

    <div class="offers-container">

      <div class="offer-card" *ngFor="let offer of offers">

        <div class="offer-content">
          <h3>{{ offer.title }}</h3>
          <p>{{ offer.description }}</p>
        </div>

        <div class="actions">

          <button class="view" (click)="viewApplications(offer.id)">
            👀 Candidatures
          </button>

          <button class="edit" (click)="editOffer(offer.id)">
            ✏ Modifier
          </button>

          <button class="delete" (click)="deleteOffer(offer.id)">
            🗑 Supprimer
          </button>

        </div>

      </div>

    </div>
  `,
  styles: [`
    .header {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      gap: 12px;
      margin-bottom: 25px;
    }

    .create-btn {
      padding: 10px 16px;
      background: #2563eb;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 500;
    }

    .offers-container {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }

    .offer-card {
      background: white;
      padding: 20px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .offer-content {
      max-width: 60%;
    }

    .actions {
      display: flex;
      gap: 10px;
    }

    .actions button {
      padding: 8px 12px;
      border-radius: 6px;
      border: none;
      cursor: pointer;
      font-size: 13px;
    }

    .view {
      background: #0f172a;
      color: white;
    }

    .edit {
      background: #f59e0b;
      color: white;
    }

    .delete {
      background: #ef4444;
      color: white;
    }
  `]
})
export class AdminOffersComponent implements OnInit {

  offers: any[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.loadOffers();
  }

  loadOffers() {
    this.http.get<any[]>('http://localhost:8080/api/offers')
      .subscribe({
        next: res => this.offers = res,
        error: err => console.error(err)
      });
  }

  goToCreate() {
    this.router.navigate(['/admin/offers/create']);
  }

  viewApplications(offerId: number) {
    this.router.navigate(['/admin/offers', offerId, 'applications']);
  }

  editOffer(offerId: number) {
    this.router.navigate(['/admin/offers/edit', offerId]);
  }

  deleteOffer(offerId: number) {

    const confirmDelete = confirm("Supprimer cette offre ?");

    if (!confirmDelete) return;

    this.http.delete(`http://localhost:8080/api/offers/${offerId}`)
      .subscribe(() => this.loadOffers());
  }
}