export interface DocumentSummary {
  id: string;
  title: string;
  documentType: string;
  origine: string;

  ownerId: string;
  ownerEmail: string;

  authorizedCount: number;

  totalSignatures: number;
  signedByCurrentUser: boolean;
}