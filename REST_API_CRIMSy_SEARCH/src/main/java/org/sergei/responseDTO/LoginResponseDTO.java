package org.sergei.responseDTO;

import org.sergei.security.support_classes.Role;

public class LoginResponseDTO {
    private String token;
    private String username;
    private Role role;

    // Constructor for JSON-B transformer in tomee plume container
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String username, Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public LoginResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginResponseDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public LoginResponseDTO setRole(Role role) {
        this.role = role;
        return this;
    }
}
