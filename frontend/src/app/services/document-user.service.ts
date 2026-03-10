import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DocumentSummary } from './document-summary.model';
export interface UserSearchResult {
  id: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class DocumentUserService {

  private api = 'http://localhost:8080/api/documents';

  constructor(private http: HttpClient) {}

  // ==============================
  // GET MY DOCUMENTS
  // ==============================

  getMyDocuments(): Observable<DocumentSummary[]> {
    return this.http.get<DocumentSummary[]>(`${this.api}/me`);
  }

  // ==============================
  // UPLOAD DOCUMENT
  // ==============================

  uploadDocument(
    file: File,
    documentType: string,
    origine: string,
    authorizedUserIds: string[],
    title: string
  ): Observable<any> {

    const formData = new FormData();

    formData.append('file', file);
    formData.append('documentType', documentType);
    formData.append('origine', origine);
    formData.append('title', title);

    authorizedUserIds.forEach(id => {
      formData.append('authorizedUserIds', id);
    });

    return this.http.post(this.api, formData);
  }

  // ==============================
  // READ DOCUMENT
  // ==============================

  readDocument(id: string, password: string): Observable<Blob> {
    return this.http.post(
      `${this.api}/${id}/read`,
      { password },
      { responseType: 'blob' }
    );
  }

  // ==============================
  // SIGN DOCUMENT
  // ==============================

  signDocument(id: string, password: string) {
    return this.http.post(`${this.api}/${id}/sign`, { password });
  }

  // ==============================
  // VERIFY DOCUMENT
  // ==============================

  verifyDocument(id: string) {
    return this.http.get<any>(`${this.api}/${id}/verify`);
  }

  // ==============================
  // DELETE DOCUMENT
  // ==============================

  deleteDocument(id: string) {
    return this.http.delete(`${this.api}/${id}`);
  }

  // ==============================
  // GET AUTHORIZED USERS
  // ==============================

  getAuthorizedUsers(id: string) {
    return this.http.get<UserSearchResult[]>(
      `${this.api}/${id}/authorized-users`
    );
  }

  // ==============================
  // SEARCH USERS
  // ==============================

  searchUsers(query: string) {
    return this.http.get<UserSearchResult[]>(
      `http://localhost:8080/api/users/search?q=${query}`
    );
  }

  // ==============================
  // SHARE DOCUMENT
  // ==============================

  shareDocument(documentId: string, userId: string) {
    return this.http.post(
      `${this.api}/${documentId}/share`,
      { userId }
    );
  }

  // ==============================
  // REMOVE SHARE
  // ==============================

  removeShare(documentId: string, userId: string) {
    return this.http.delete(
      `${this.api}/${documentId}/share/${userId}`
    );
  }
// ==============================
// GET SHARED WITH ME
// ==============================

getSharedWithMe(): Observable<DocumentSummary[]> {
  return this.http.get<DocumentSummary[]>(`${this.api}/shared-with-me`);
}

// ==============================
// GET SHARED BY ME
// ==============================

getSharedByMe(): Observable<DocumentSummary[]> {
  return this.http.get<DocumentSummary[]>(`${this.api}/shared-by-me`);
}
// ==============================
// GET DOCUMENT HISTORY
// ==============================

getHistory(documentId: string) {
  return this.http.get<any[]>(`${this.api}/${documentId}/history`);
}

}