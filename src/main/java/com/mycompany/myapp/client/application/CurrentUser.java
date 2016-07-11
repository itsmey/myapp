package com.mycompany.myapp.client.application;

public class CurrentUser {
    private boolean loggedIn;

    public void setLoggedIn() {
        loggedIn = true;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}