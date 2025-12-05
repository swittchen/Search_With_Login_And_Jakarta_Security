package org.sergei.security.support_classes;

import java.time.Instant;

public class AuthToken {

    private final String token;
    private final String username;
    private final Role role;
    private final Instant expireAt;

    public AuthToken(String token, String username, Role role, Instant expireAt) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public boolean isExpired(){
        return expireAt != null && Instant.now().isAfter(expireAt);
    }
}
