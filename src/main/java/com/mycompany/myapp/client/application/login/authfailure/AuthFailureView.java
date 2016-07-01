package com.mycompany.myapp.client.application.login.authfailure;

import javax.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class AuthFailureView extends PopupViewImpl implements AuthFailurePresenter.MyView {
    interface Binder extends UiBinder<PopupPanel, AuthFailureView> {
    }

    @Inject
    AuthFailureView(Binder uiBinder,
                    EventBus eventBus) {
        super(eventBus);
        initWidget(uiBinder.createAndBindUi(this));
    }
}