import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDocumentMonitorService } from '../../../services/admin-document-monitor.service';

@Component({
  selector: 'app-admin-documents-monitor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-documents-monitor.component.html',
  styleUrls: ['./admin-documents-monitor.component.css']
})
export class AdminDocumentsMonitorComponent implements OnInit {

  documents:any[] = [];
  selectedDocument:any = null;

  constructor(private service:AdminDocumentMonitorService){}

  ngOnInit(){
    this.loadDocuments();
  }

  loadDocuments(){
    this.service.getAllDocuments()
      .subscribe(res=>{
        this.documents = res;
      });
  }

  view(doc:any){
    this.selectedDocument = doc;
  }

  closeModal(){
    this.selectedDocument = null;
  }

  delete(id:string){

    const confirmDelete = confirm(
      "⚠️ Êtes-vous sûr de vouloir supprimer ce document ?"
    );

    if(!confirmDelete) return;

    this.service.deleteDocument(id)
      .subscribe(()=>{
        this.loadDocuments();
        this.closeModal();
      });
  }

}