import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminIdentityService } from '../../../services/admin-identity.service';

@Component({
  selector: 'app-identity-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './identity-list.component.html',
  styleUrls: ['./identity-list.component.css']
})
export class IdentityListComponent implements OnInit {

  users: any[] = [];
  selectedUser: any = null;

  constructor(private identityService: AdminIdentityService) {}

  ngOnInit(): void {
    this.loadPending();
  }

  // Charger identités en attente
  loadPending() {
    this.identityService.getPending()
      .subscribe(res => {
        this.users = res;
      });
  }

  // Ouvrir modal
  openModal(user: any) {
    this.selectedUser = user;
  }

  // Fermer modal
  closeModal() {
    this.selectedUser = null;
  }

  // Approuver identité
  approve(userId: number) {

  this.identityService.approve(userId)
    .subscribe(() => {

      alert("Utilisateur approuvé ✅");

      // supprimer de la liste
      this.users = this.users.filter(user => user.id !== userId);

      this.closeModal();

    });

}

  // Refuser identité
reject(userId: number) {

  this.identityService.reject(userId)
    .subscribe(() => {

      alert("Utilisateur rejeté ❌");

      this.users = this.users.filter(user => user.id !== userId);

      this.closeModal();

    });

}

  // Style status
  getStatusClass(status: string) {

    if (status === 'PENDING_VERIFICATION')
      return 'status-pending';

    if (status === 'VERIFIED')
      return 'status-approved';

    if (status === 'REJECTED')
      return 'status-rejected';

    return '';
  }

}