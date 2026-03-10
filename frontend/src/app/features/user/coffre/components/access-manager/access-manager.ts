import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentUserService } from '../../../../../services/document-user.service';

interface UserSearchResult {
  id: string;
  email: string;
}

@Component({
  selector: 'app-access-manager',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './access-manager.html',
  styleUrls: ['./access-manager.css']
})
export class AccessManagerComponent implements OnInit {

  @Input() documentId!: string;

  // ✅ PROPREMENT TYPOLOGIÉ
  authorizedUsers: UserSearchResult[] = [];

  loading = false;

  // 🔎 Search
  searchQuery = '';
  searchResults: UserSearchResult[] = [];
  loadingSearch = false;

  constructor(private documentService: DocumentUserService) {}

  // =============================
  // INIT
  // =============================

  ngOnInit(): void {
    if (this.documentId) {
      this.loadAuthorizedUsers();
    }
  }

  // =============================
  // LOAD AUTHORIZED USERS
  // =============================

  loadAuthorizedUsers(): void {
    if (!this.documentId) return;

    this.loading = true;

    this.documentService.getAuthorizedUsers(this.documentId)
      .subscribe({
        next: (users: UserSearchResult[]) => {
          this.authorizedUsers = users ?? [];
          this.loading = false;
        },
        error: (err) => {
          console.error('Erreur chargement accès', err);
          this.loading = false;
        }
      });
  }

  // =============================
  // SEARCH USERS
  // =============================

  onSearchChange(): void {

    if (!this.searchQuery || this.searchQuery.length < 2) {
      this.searchResults = [];
      return;
    }

    this.loadingSearch = true;

    this.documentService.searchUsers(this.searchQuery)
      .subscribe({
        next: (users: UserSearchResult[]) => {
          this.searchResults = users ?? [];
          this.loadingSearch = false;
        },
        error: (err) => {
          console.error('Erreur recherche:', err);
          this.loadingSearch = false;
        }
      });
  }

  // =============================
  // ADD USER
  // =============================

  addUser(user: UserSearchResult): void {

    if (!this.documentId || !user?.id) return;

    this.documentService.shareDocument(this.documentId, user.id)
      .subscribe({
        next: () => {
          this.searchQuery = '';
          this.searchResults = [];
          this.loadAuthorizedUsers();
        },
        error: (err) => {
          console.error('Erreur partage:', err);
        }
      });
  }

  // =============================
  // REMOVE USER
  // =============================

  removeUser(userId: string): void {

    if (!this.documentId) return;

    this.documentService.removeShare(this.documentId, userId)
      .subscribe({
        next: () => {
          this.loadAuthorizedUsers();
        },
        error: (err) => {
          console.error('Erreur suppression accès:', err);
        }
      });
  }
}