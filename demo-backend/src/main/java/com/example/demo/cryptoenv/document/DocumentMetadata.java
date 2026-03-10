package com.example.demo.cryptoenv.document;

import java.time.Instant;

public class DocumentMetadata {

    private final String documentId;
    private final String typeDocument;
    private final String origine;
    private final Instant dateDepot;
    private final String hash;
    private final String statut;

    public DocumentMetadata(
            String documentId,
            String typeDocument,
            String origine,
            Instant dateDepot,
            String hash,
            String statut
    ) {
        this.documentId = documentId;
        this.typeDocument = typeDocument;
        this.origine = origine;
        this.dateDepot = dateDepot;
        this.hash = hash;
        this.statut = statut;
    }

    public String getDocumentId() { return documentId; }
    public String getTypeDocument() { return typeDocument; }
    public String getOrigine() { return origine; }
    public Instant getDateDepot() { return dateDepot; }
    public String getHash() { return hash; }
    public String getStatut() { return statut; }
}
