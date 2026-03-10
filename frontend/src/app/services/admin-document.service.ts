import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminDocumentService {

  private baseUrl = 'http://localhost:8080/api/admin/documents';

  constructor(private http: HttpClient) {}

  getUserDocuments(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }

  downloadDocument(documentId: number, adminId: number): Observable<Blob> {
    return this.http.get(
      `${this.baseUrl}/${documentId}/${adminId}`,
      { responseType: 'blob' }
    );
  }
}