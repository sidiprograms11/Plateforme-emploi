import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminLogService {

  private api = "http://localhost:8080/api/admin/logs";

  constructor(private http:HttpClient){}

  getLogs(){
    return this.http.get<any[]>(this.api);
  }

}