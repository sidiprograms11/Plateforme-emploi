package com.example.demo.cryptoenv.document;

import com.example.demo.identity.Permission;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public enum SecureDocumentType {

    // 📄 Documents CV / Candidature
    CV(Set.of(
            Permission.LIRE_ADMINISTRATIF,
            Permission.EVALUER_CV
    )),

    LETTRE_MOTIVATION(Set.of(
            Permission.LIRE_ADMINISTRATIF
    )),

    // 💰 Documents financiers
    FICHE_PAIE(Set.of(
            Permission.LIRE_FINANCIER
    )),

    FINANCIER(Set.of(
            Permission.LIRE_FINANCIER
    )),

    CONFIDENTIAL(Set.of(
            Permission.LIRE_FINANCIER
    )),

    ADMINISTRATIF(Set.of(
            Permission.LIRE_FINANCIER
    )),

    RELEVE_BANCAIRE(Set.of(
            Permission.LIRE_FINANCIER
    )),

    // 🆔 Documents utilisateur
    PIECE_IDENTITE(Set.of(
            Permission.LIRE_UTILISATEUR
    )),

    JUSTIFICATIF_DOMICILE(Set.of(
            Permission.LIRE_UTILISATEUR
    )),

    DOCUMENT_PERSONNEL(Set.of(
            Permission.LIRE_UTILISATEUR
    )),

    // 📂 Documents généraux
    CONTRAT(Set.of(
            Permission.LIRE_ADMINISTRATIF
    )),

    AUTRE(Set.of(
            Permission.LIRE_ADMINISTRATIF
    ));

    private final Set<Permission> requiredPermissions;

    SecureDocumentType(Set<Permission> requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
    }

    public Set<Permission> getRequiredPermissions() {
        return Collections.unmodifiableSet(requiredPermissions);
    }

    // 🔥 VERSION ROBUSTE
    public static SecureDocumentType from(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("documentType est null ou vide");
        }

        String normalized = value.trim().toUpperCase();

        return Arrays.stream(SecureDocumentType.values())
                .filter(type -> type.name().equals(normalized))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Type de document invalide : " + value)
                );
    }
}