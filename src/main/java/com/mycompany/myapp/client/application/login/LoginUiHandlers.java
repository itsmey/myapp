package com.mycompany.myapp.client.application.login;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LoginUiHandlers extends UiHandlers {
    void onConnect(String login, String password);
}