import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminDocumentMonitorService {

  private api = "http://localhost:8080/api/admin/documents";

  constructor(private http: HttpClient) {}

  getAllDocuments(){
    return this.http.get<any[]>(this.api);
  }

 deleteDocument(id:string){
  return this.http.delete(
    `http://localhost:8080/api/admin/documents/${id}`
  );
}

}