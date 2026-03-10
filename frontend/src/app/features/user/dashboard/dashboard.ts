import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';
import { OfferService } from '../../../services/offer.service';
import { DocumentUserService } from '../../../services/document-user.service';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
styleUrls: ['./dashboard.css']
})
export class UserDashboardComponent implements OnInit {

  user: any;
  documentsCount = 0;
  applicationsCount = 0;

  constructor(
    private authService: AuthService,
    private offerService: OfferService,
    private documentService: DocumentUserService
  ) {}

  ngOnInit(): void {

    this.user = this.authService.getCurrentUser();

    this.documentService.getMyDocuments()
      .subscribe((docs: any) => {
        this.documentsCount = docs.length;
      });

    this.offerService.getMyApplications()
      .subscribe((apps: any) => {
        this.applicationsCount = apps.length;
      });
  }

  getStatusClass() {
    return this.user?.status === 'VERIFIED'
      ? 'verified'
      : 'pending';
  }
}