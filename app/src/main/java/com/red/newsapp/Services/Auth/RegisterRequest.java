package com.red.newsapp.Services.Auth;

public class RegisterRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;

    public RegisterRequest(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
