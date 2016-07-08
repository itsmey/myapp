package com.mycompany.myapp.client.application.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
    void loginUser(String login, String password, AsyncCallback<Void> callback);
    void logoutUser(AsyncCallback<Void> callback);
}