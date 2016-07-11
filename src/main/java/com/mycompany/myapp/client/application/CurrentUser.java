package com.mycompany.myapp.client.application;

public class CurrentUser {
    private boolean loggedIn;

    public void setLoggedIn() {
        loggedIn = true;
    }

    public void setLoggedOut() {
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}