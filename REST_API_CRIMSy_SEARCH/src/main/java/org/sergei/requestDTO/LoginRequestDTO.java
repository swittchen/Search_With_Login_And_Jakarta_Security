package org.sergei.requestDTO;

import jakarta.json.bind.annotation.JsonbProperty;

public class LoginRequestDTO {
    @JsonbProperty("username")
    private String username;
    @JsonbProperty("password")
    private String password;

    private String status;

    // Constructor for JSON-B transformer in tomee plume container
    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public LoginRequestDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public LoginRequestDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
