package com.mycompany.myapp.client.application.login.authfailure;

import javax.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AuthFailurePresenter extends PresenterWidget<AuthFailurePresenter.MyView> {
    public interface MyView extends PopupView {
    }

    @Inject
    AuthFailurePresenter(EventBus eventBus,
                         MyView view) {
        super(eventBus, view);
    }
}