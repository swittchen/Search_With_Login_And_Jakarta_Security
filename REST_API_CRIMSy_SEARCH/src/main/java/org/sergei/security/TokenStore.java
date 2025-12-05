package org.sergei.security;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import org.sergei.security.support_classes.AuthToken;
import org.sergei.security.support_classes.Role;
import org.sergei.security.support_classes.UserAccount;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TokenStore {
    @PostConstruct
    public void init() {
        System.out.println("TokenStore CDI init: " + this);
    }


    private final Map<String, AuthToken> tokens = new ConcurrentHashMap<>();
    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();

    public TokenStore() {
        users.put("admin", new UserAccount("admin", "admin", Role.ADMIN));
        users.put("user", new UserAccount("user", "user", Role.USER));
        users.put("guest", new UserAccount("guest", "guest", Role.GUEST));
    }

    public AuthToken authenticate(String username, String password) {
        UserAccount user = users.get(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        String tokenValue = UUID.randomUUID().toString();
        Instant expires = Instant.now().plus(30, ChronoUnit.MINUTES);
        AuthToken token = new AuthToken(tokenValue, user.getUserName(), user.getRole(), expires);
        tokens.put(tokenValue, token);
        return token;
    }

    public AuthToken findByToken(String tokenValue) {
        if (tokenValue == null) {
            return null;
        }
        AuthToken token = tokens.get(tokenValue);
        if (token == null) {
            return null;
        }
        if (token.isExpired()) {
            tokens.remove(tokenValue);
            return null;
        }
        return token;
    }

    public void revoke(String tokenValue) {
        if (tokenValue != null) {
            tokens.remove(tokenValue);
        }
    }
}
