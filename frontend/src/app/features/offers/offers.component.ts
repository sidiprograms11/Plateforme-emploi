import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OfferService } from '../../services/offer.service';

@Component({
  selector: 'app-offers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  offers: any[] = [];
  selectedOfferId!: number;
  applications: any[] = [];

  constructor(private offerService: OfferService) {}

  ngOnInit(): void {
    this.loadOffers();
  }

  loadOffers() {
    this.offerService.getOffers().subscribe(res => {
      this.offers = res;
    });
  }

  apply(offerId: number) {
    this.offerService.applyToOffer(offerId).subscribe(() => {
      alert('Candidature envoyée');
    });
  }

  loadApplications(offerId: number) {
    this.selectedOfferId = offerId;
    this.offerService.getApplications(offerId).subscribe(res => {
      this.applications = res;
    });
  }

  approve(applicationId: number) {
    this.offerService
      .approveApplication(this.selectedOfferId, applicationId)
      .subscribe(() => this.loadApplications(this.selectedOfferId));
  }

  reject(applicationId: number) {
    this.offerService
      .rejectApplication(this.selectedOfferId, applicationId)
      .subscribe(() => this.loadApplications(this.selectedOfferId));
  }
}