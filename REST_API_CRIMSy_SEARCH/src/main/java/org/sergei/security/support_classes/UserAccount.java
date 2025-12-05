package org.sergei.security.support_classes;

public class UserAccount {
    private String userName;
    private String password;
    private Role role;

    public UserAccount(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
