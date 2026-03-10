package com.example.demo.cryptoenv.api.dto;

public class UserSearchResponse {

    private String id;
    private String email;

    public UserSearchResponse(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}