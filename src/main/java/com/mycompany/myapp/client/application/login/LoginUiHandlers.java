package com.mycompany.myapp.client.application.login;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LoginUiHandlers extends UiHandlers {
    void onLogin(String login, String password);
}