import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-offer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h1>✏ Modifier l'offre</h1>

      <div class="form-card" *ngIf="offer">

        <label>Titre</label>
        <input [(ngModel)]="offer.title" name="title"/>

        <label>Description</label>
        <textarea [(ngModel)]="offer.description" name="description"></textarea>

        <div class="buttons">
          <button class="save" (click)="update()">💾 Enregistrer</button>
          <button class="cancel" (click)="cancel()">Annuler</button>
        </div>

      </div>
    </div>
  `,
  styles: [`
    .container {
      max-width: 700px;
    }

    .form-card {
      background: white;
      padding: 25px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
      display: flex;
      flex-direction: column;
      gap: 15px;
    }

    input, textarea {
      padding: 10px;
      border-radius: 8px;
      border: 1px solid #e5e7eb;
    }

    textarea {
      min-height: 120px;
    }

    .buttons {
      display: flex;
      gap: 10px;
      margin-top: 10px;
    }

    .save {
      background: #2563eb;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 8px;
      cursor: pointer;
    }

    .cancel {
      background: #e5e7eb;
      border: none;
      padding: 10px 15px;
      border-radius: 8px;
      cursor: pointer;
    }
  `]
})
export class EditOfferComponent implements OnInit {

  offer: any = null;
  offerId!: number;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if(id){
      this.offerId = Number(id);
      this.loadOffer();
    }

  }

  loadOffer() {
    this.http
      .get<any>(`http://localhost:8080/api/offers/${this.offerId}`)
      .subscribe(res => {
        this.offer = res;
      });
  }

  update() {

    this.http.put(
      `http://localhost:8080/api/offers/${this.offerId}`,
      {
        title: this.offer.title,
        description: this.offer.description
      }
    ).subscribe(() => {

      alert('Offre modifiée avec succès');

      this.router.navigate(['/admin/offers']);

    });

  }

  cancel() {
    this.router.navigate(['/admin/offers']);
  }

}