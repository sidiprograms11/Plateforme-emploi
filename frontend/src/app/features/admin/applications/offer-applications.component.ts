import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-offer-applications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './offer-applications.component.html',
  styleUrls: ['./offer-applications.component.css']
})
export class OfferApplicationsComponent implements OnInit {

  applications: any[] = [];
  offerId!: number;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if(id){
      this.offerId = Number(id);
      this.loadApplications();
    }

  }

  loadApplications() {

    this.http
      .get<any[]>(`http://localhost:8080/api/offers/${this.offerId}/applications`)
      .subscribe(res => {
        this.applications = res;
      });

  }

  approve(applicationId: number) {

    this.http.post(
      `http://localhost:8080/api/offers/${this.offerId}/applications/${applicationId}/approve`,
      {}
    ).subscribe(() => {

      alert("Candidature approuvée");

      this.loadApplications();

    });

  }

  reject(applicationId: number) {

    this.http.post(
      `http://localhost:8080/api/offers/${this.offerId}/applications/${applicationId}/reject`,
      {}
    ).subscribe(() => {

      alert("Candidature rejetée");

      this.loadApplications();

    });

  }

  getStatusClass(status: string) {

    if (status === 'PENDING') return 'status-pending';
    if (status === 'APPROVED') return 'status-approved';
    if (status === 'REJECTED') return 'status-rejected';

    return '';

  }

}