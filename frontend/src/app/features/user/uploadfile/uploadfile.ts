import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { DocumentUserService, UserSearchResult } from '../../../services/document-user.service';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './uploadfile.html',
  styleUrls: ['./uploadfile.css']
})
export class UploadComponent implements OnDestroy {

  // 🔹 Nouveau champ title
  title = '';

  selectedFile!: File;
  documentType = 'AUTRE';
  origine = 'UPLOAD_USER';
  authorizedUserIds: string[] = [];

  message = '';

  // 🔎 Recherche utilisateurs
  searchQuery = '';
  searchResults: UserSearchResult[] = [];
  selectedUsers: UserSearchResult[] = [];
  loadingUsers = false;

  private searchSubject = new Subject<string>();

  constructor(private documentService: DocumentUserService) {

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(query => {
        this.loadingUsers = true;
        return this.documentService.searchUsers(query);
      })
    ).subscribe(users => {
      this.searchResults = users || [];
      this.loadingUsers = false;
    });
  }

  // ===============================
  // FILE
  // ===============================

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  // ===============================
  // SEARCH USERS
  // ===============================

  onSearchChange() {
    if (!this.searchQuery || this.searchQuery.length < 2) {
      this.searchResults = [];
      return;
    }

    this.searchSubject.next(this.searchQuery);
  }

  selectUser(user: UserSearchResult) {

    if (!this.selectedUsers.find(u => u.id === user.id)) {
      this.selectedUsers.push(user);
      this.authorizedUserIds.push(user.id);
    }

    this.searchResults = [];
    this.searchQuery = '';
  }

  removeUser(user: UserSearchResult) {
    this.selectedUsers =
      this.selectedUsers.filter(u => u.id !== user.id);

    this.authorizedUserIds =
      this.authorizedUserIds.filter(id => id !== user.id);
  }

  // ===============================
  // UPLOAD
  // ===============================

  upload() {
    if (!this.selectedFile || !this.title) {
      this.message = '❌ Veuillez remplir tous les champs';
      return;
    }

    this.documentService.uploadDocument(
      this.selectedFile,
      this.documentType,
      this.origine,
      this.authorizedUserIds,
      this.title   // 🔥 IMPORTANT
    ).subscribe({
      next: () => {
        this.message = '✅ Document ajouté au coffre';

        // Reset form
        this.title = '';
        this.selectedFile = undefined as any;
        this.selectedUsers = [];
        this.authorizedUserIds = [];
      },
      error: () => {
        this.message = '❌ Erreur upload';
      }
    });
  }

  ngOnDestroy() {
    this.searchSubject.complete();
  }
}