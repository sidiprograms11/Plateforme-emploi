import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DocumentUserService } 
from '../../../../../services/document-user.service';

import { DocumentSummary } 
from '../../../../../services/document-summary.model';

@Component({
  selector: 'app-shared-by-me',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './shared-by-me.component.html',
  styleUrls: ['./shared-by-me.component.scss']
})
export class SharedByMeComponent implements OnInit {

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

    this.documentService.getSharedByMe().subscribe({
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
}