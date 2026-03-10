import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminUserService } from '../../../services/admin-user.service';

@Component({
  selector:'app-admin-users',
  standalone:true,
  imports:[CommonModule,FormsModule],
  templateUrl:'./admin-users.component.html',
  styleUrls:['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit{

  users:any[]=[];
  search="";

  constructor(private service:AdminUserService){}

  ngOnInit(){
    this.loadUsers();
  }

  loadUsers(){
    this.service.getUsers()
      .subscribe(res=>this.users=res);
  }

  searchUser(){
    if(this.search.trim()===""){
      this.loadUsers();
      return;
    }

    this.service.searchUsers(this.search)
      .subscribe(res=>this.users=res);
  }

  suspend(id:number){
    this.service.suspendUser(id)
      .subscribe(()=>this.loadUsers());
  }

  promote(id:number){
    this.service.promoteUser(id)
    
      .subscribe(()=>this.loadUsers());
  }

  delete(id:number){

 const confirmDelete = confirm(
   "⚠️ Êtes-vous sûr de vouloir supprimer cet utilisateur ?"
 );

 if(!confirmDelete) return;

 this.service.deleteUser(id)
   .subscribe(()=>{
     alert("Utilisateur supprimé");
     this.loadUsers();
   });

}

}