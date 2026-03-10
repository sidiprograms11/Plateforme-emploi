import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-offer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h1>➕ Créer une Offre</h1>

    <form (ngSubmit)="createOffer()" class="form">

      <label>Titre</label>
      <input [(ngModel)]="title" name="title" required />

      <label>Description</label>
      <textarea [(ngModel)]="description" name="description" required></textarea>

      <button type="submit">Créer</button>

    </form>
  `,
  styles: [`
    .form {
      background: white;
      padding: 30px;
      border-radius: 12px;
      max-width: 500px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
    }

    label {
      display: block;
      margin-top: 15px;
      margin-bottom: 5px;
    }

    input, textarea {
      width: 100%;
      padding: 10px;
      border-radius: 8px;
      border: 1px solid #cbd5e1;
    }

    button {
      margin-top: 20px;
      padding: 10px 15px;
      border: none;
      border-radius: 8px;
      background: #2563eb;
      color: white;
      cursor: pointer;
    }
  `]
})
export class CreateOfferComponent {

  title = '';
  description = '';

  constructor(private http: HttpClient, private router: Router) {}

  createOffer() {

    const payload = {
      title: this.title,
      description: this.description
    };

    this.http.post('http://localhost:8080/api/offers', payload)
      .subscribe({
        next: () => {
          alert("Offre créée avec succès");
          this.router.navigate(['/admin/offers']);
        },
        error: (err) => {
          console.error(err);
          alert("Erreur lors de la création de l'offre");
        }
      });

  }
}