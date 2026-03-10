package com.example.demo.api.registration.dto;

public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String identityDocumentBase64;
    private String identityPhotoBase64;

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getIdentityDocumentBase64() {
        return identityDocumentBase64;
    }

    public String getIdentityPhotoBase64() {
        return identityPhotoBase64;
    }
}
