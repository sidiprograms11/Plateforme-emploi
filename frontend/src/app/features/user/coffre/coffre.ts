import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';

import { DocumentUserService }
from '../../../services/document-user.service';

import { DocumentSummary }
from '../../../services/document-summary.model';

import { SharedWithMeComponent } 
from './components/shared-with-me/shared-with-me.component';

import { SharedByMeComponent } 
from './components/shared-by-me/shared-by-me.component';

import { AccessManagerComponent } 
from './components/access-manager/access-manager';



// ===============================
// 📜 HISTORY MODEL
// ===============================

interface DocumentHistory {

  documentId: string;
  documentTitle: string;
  documentType: string;

  ownerId: string;
  ownerEmail: string;

  actionType: string;

  actorId: string;
  actorEmail: string;

  targetUserId?: string;
  targetUserEmail?: string;

  totalAuthorizedUsers: number;

  timestamp: string;

}


@Component({
  selector: 'app-coffre',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    SharedWithMeComponent,
    SharedByMeComponent,
    AccessManagerComponent
  ],
  templateUrl: './coffre.html',
  styleUrls: ['./coffre.css']
})


export class CoffreComponent implements OnInit {


  documents: DocumentSummary[] = [];
  loading = false;
  error = '';

  activeTab: 'mine' | 'shared-with-me' | 'shared-by-me' = 'mine';


  setTab(tab: 'mine' | 'shared-with-me' | 'shared-by-me') {
    this.activeTab = tab;
  }


  previewOpen = false;
  previewUrl: SafeResourceUrl | null = null;
  previewPassword = '';
  unlocked = false;
  selectedDoc?: DocumentSummary;

  private unlockPassword = '';


  constructor(
    private documentService: DocumentUserService,
    private sanitizer: DomSanitizer,
    private http: HttpClient
  ) {}


  // ===============================
  // INIT
  // ===============================

  ngOnInit(): void {
    this.loadDocuments();
  }


  // ===============================
  // LOAD DOCUMENTS
  // ===============================

  loadDocuments(): void {

    this.loading = true;

    this.documentService.getMyDocuments().subscribe({

      next: (docs: DocumentSummary[]) => {

        this.documents = docs;
        this.loading = false;

      },

      error: () => {

        this.error = 'Impossible de charger les documents';
        this.loading = false;

      }

    });

  }


  // ===============================
  // PREVIEW
  // ===============================

  preparePreview(doc: DocumentSummary): void {

    this.selectedDoc = doc;
    this.previewOpen = true;

    this.unlocked = false;
    this.previewUrl = null;

    this.previewPassword = '';
    this.unlockPassword = '';

    document.body.classList.add('modal-open');

  }


  confirmUnlock(): void {

    if (!this.selectedDoc) return;

    this.documentService
      .readDocument(this.selectedDoc.id, this.previewPassword)
      .subscribe({

        next: (blob: Blob) => {

          const url = URL.createObjectURL(blob);

          this.previewUrl =
            this.sanitizer.bypassSecurityTrustResourceUrl(url);

          this.unlocked = true;
          this.unlockPassword = this.previewPassword;

        },

        error: () => {
          alert('Mot de passe incorrect');
        }

      });

  }


  // ===============================
  // DOWNLOAD
  // ===============================

  download(doc: DocumentSummary): void {

    const password = prompt('Mot de passe pour télécharger');

    if (!password) return;

    this.documentService.readDocument(doc.id, password)
      .subscribe({

        next: (blob: Blob) => {

          const url = window.URL.createObjectURL(blob);

          const a = document.createElement('a');
          a.href = url;
          a.download = doc.documentType || 'document';
          a.click();

          window.URL.revokeObjectURL(url);

        },

        error: () => {
          alert('Erreur téléchargement');
        }

      });

  }


  // ===============================
  // CLOSE PREVIEW
  // ===============================

  closePreview(): void {

    this.previewOpen = false;
    this.previewUrl = null;

    this.previewPassword = '';
    this.unlocked = false;
    this.unlockPassword = '';

    document.body.classList.remove('modal-open');

  }


  // ===============================
  // HISTORY
  // ===============================

  historyOpen = false;
  historyLoading = false;

  documentHistory: DocumentHistory[] = [];

openHistory(doc: DocumentSummary) {

  this.historyOpen = true;
  this.historyLoading = true;

  this.documentService
    .getHistory(doc.id)
    .subscribe({

      next: (data: DocumentHistory[]) => {
        this.documentHistory = data;
        this.historyLoading = false;
      },

      error: (err: any) => {
        console.error("History error", err);
        this.historyLoading = false;
      }

    });

}

closeHistory(): void {

  this.historyOpen = false;
  this.documentHistory = [];
  this.historyLoading = false;

}
  getHistoryIcon(action: string): string {

    switch (action) {

      case 'UPLOAD': return '📤';
      case 'READ': return '👁';
      case 'SIGN': return '✍️';
      case 'DELETE': return '🗑';
      case 'SHARE': return '📩';
      case 'REMOVE_SHARE': return '❌';
      case 'VERIFY': return '🔎';

      default: return '📄';

    }

  }


  // ===============================
  // SIGN DOCUMENT
  // ===============================

  signDocument(doc: DocumentSummary): void {

    if (!this.unlocked || !this.unlockPassword) {

      alert("Veuillez d'abord déverrouiller le document");
      return;

    }

    this.documentService.signDocument(doc.id, this.unlockPassword)
      .subscribe({

        next: () => {

          alert('Document signé avec succès');

          doc.signedByCurrentUser = true;
          doc.totalSignatures++;

        },

        error: () => {

          alert('Erreur lors de la signature');

        }

      });

  }


  // ===============================
  // DELETE DOCUMENT
  // ===============================

  deleteDocument(doc: DocumentSummary): void {

    const confirmDelete = confirm(
      `Supprimer le document "${doc.documentType}" ?`
    );

    if (!confirmDelete) return;

    this.documentService.deleteDocument(doc.id)
      .subscribe({

        next: () => {

          this.documents =
            this.documents.filter(d => d.id !== doc.id);

          alert("Document supprimé avec succès");

        },

        error: () => {

          alert("Erreur lors de la suppression");

        }

      });

  }


  // ===============================
  // ACCESS MANAGER
  // ===============================

  manageOpen = false;
  manageDoc: DocumentSummary | null = null;


  openManageAccess(doc: DocumentSummary) {

    this.manageDoc = doc;
    this.manageOpen = true;

  }


  closeManage() {

    this.manageOpen = false;
    this.manageDoc = null;

  }

}