import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DocumentSummary } from './document-summary.model';

export interface DocumentHistory {
  actionType: string;
  userId: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class DocumentAccessService {

  private api = 'http://localhost:8080/api/documents';

  constructor(private http: HttpClient) {}

  getMyDocuments(): Observable<DocumentSummary[]> {
    return this.http.get<DocumentSummary[]>(`${this.api}/me`);
  }

  getSharedWithMe(): Observable<DocumentSummary[]> {
    return this.http.get<DocumentSummary[]>(`${this.api}/shared-with-me`);
  }

  shareDocument(documentId: string, userId: string) {
    return this.http.post(`${this.api}/${documentId}/share`, { userId });
  }

  removeShare(documentId: string, userId: string) {
    return this.http.delete(`${this.api}/${documentId}/share/${userId}`);
  }

  getHistory(documentId: string): Observable<DocumentHistory[]> {
    return this.http.get<DocumentHistory[]>(`${this.api}/${documentId}/history`);
  }

  getAuthorizedUsers(documentId: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.api}/${documentId}/authorized-users`);
  }
}