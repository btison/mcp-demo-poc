package org.globex.ai.service;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AuthoritativeUserIdHolder {

    private String userId;

    public AuthoritativeUserIdHolder() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
