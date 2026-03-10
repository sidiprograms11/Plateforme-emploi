import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminUserService {

  private adminApi = "http://localhost:8080/api/admin/users";
  private searchApi = "http://localhost:8080/api/users/search";

  constructor(private http: HttpClient) {}

  getUsers(){
    return this.http.get<any[]>(this.adminApi);
  }

  searchUsers(query:string){
    return this.http.get<any[]>(`${this.searchApi}?query=${query}`);
  }

  suspendUser(id:number){
    return this.http.post(`${this.adminApi}/${id}/suspend`, {});
  }

  promoteUser(id:number){
    return this.http.post(`${this.adminApi}/${id}/promote`, {});
  }

  deleteUser(id:number){
    return this.http.delete(`${this.adminApi}/${id}`);
  }
// ==============================
// GET DOCUMENT HISTORY
// ==============================

getHistory(documentId: string) {
  return this.http.get<any[]>(`${this.adminApi}/${documentId}/history`);
}
}