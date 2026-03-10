import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DocumentUserService } 
from '../../../../../services/document-user.service';

import { DocumentSummary } 
from '../../../../../services/document-summary.model';

@Component({
  selector: 'app-shared-with-me',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './shared-with-me.component.html',
  styleUrls: ['./shared-with-me.component.scss']
})
export class SharedWithMeComponent implements OnInit {

  documents: DocumentSummary[] = [];
  loading = true;
  error: string | null = null;

  @Output() readDocumentEvent = new EventEmitter<DocumentSummary>();
  @Output() historyEvent = new EventEmitter<DocumentSummary>();

  constructor(private documentService: DocumentUserService) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments() {
  this.loading = true;

  this.documentService.getSharedWithMe().subscribe({
    next: (docs: DocumentSummary[]) => {
      this.documents = docs;
      this.loading = false;
    },
    error: () => {
      this.error = 'Erreur lors du chargement';
      this.loading = false;
    }
  });
}

  readDocument(doc: DocumentSummary) {
    this.readDocumentEvent.emit(doc);
  }

  openHistory(doc: DocumentSummary) {
    this.historyEvent.emit(doc);
  }

  signDocument(doc: DocumentSummary): void {

    if (doc.signedByCurrentUser) return;

    const password = prompt("Mot de passe pour signer");
    if (!password) return;

    this.documentService.signDocument(doc.id, password)
      .subscribe({
        next: () => {
          alert("Document signé !");
          doc.signedByCurrentUser = true;
          doc.totalSignatures++;
        },
        error: () => {
          alert("Erreur lors de la signature");
        }
      });
  }
}