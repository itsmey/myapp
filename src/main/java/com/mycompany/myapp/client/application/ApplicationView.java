package com.mycompany.myapp.client.application;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers>
        implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, ApplicationView> {
    }

    @UiField
    TextBox loginField;
    @UiField
    Button loginButton;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("loginButton")
    void onLogin(ClickEvent event) {
    }
}
