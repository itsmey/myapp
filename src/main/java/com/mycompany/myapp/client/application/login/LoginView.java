package com.mycompany.myapp.client.application.login;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;

public class LoginView extends ViewWithUiHandlers<LoginUiHandlers>
        implements LoginPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, LoginView> {
    }

    @UiField
    TextBox loginField;
    @UiField
    PasswordTextBox passwordField;
    @UiField
    Button loginButton;
    @UiField
    Button connectButton;

    @Inject
    LoginView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("loginButton")
    void onLogin(ClickEvent event) {
        getUiHandlers().onLogin(loginField.getText(), passwordField.getText());
    }

    @UiHandler("connectButton")
    void onConnect(ClickEvent event) {
        getUiHandlers().onConnect(loginField.getText(), passwordField.getText());
    }
}