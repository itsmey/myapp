package com.mycompany.myapp.client.application;

public class CurrentUser {
    public static final String TEST_LOGIN = "user";
    public static final String TEST_PASS = "qwerty";
    private boolean loggedIn;

    public void setLoggedIn() {
        loggedIn = true;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}