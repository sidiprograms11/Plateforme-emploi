import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  private baseUrl = 'http://localhost:8080/api/offers';

  constructor(private http: HttpClient) {}

  getOffers(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  applyToOffer(offerId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${offerId}/apply`, {});
  }

 getMyApplications(): Observable<any[]> {
  return this.http.get<any[]>('http://localhost:8080/api/applications/me');
}

  getApplications(offerId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${offerId}/applications`);
  }

  approveApplication(offerId: number, applicationId: number): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${offerId}/applications/${applicationId}/approve`,
      {}
    );
  }

  rejectApplication(offerId: number, applicationId: number): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${offerId}/applications/${applicationId}/reject`,
      {}
    );
  }
}