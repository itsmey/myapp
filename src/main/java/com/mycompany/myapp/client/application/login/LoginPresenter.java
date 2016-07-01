package com.mycompany.myapp.client.application.login;

import javax.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.mycompany.myapp.client.application.CurrentUser;
import com.mycompany.myapp.client.application.login.authfailure.AuthFailurePresenter;
import com.mycompany.myapp.client.place.NameTokens;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy>
        implements LoginUiHandlers {
    interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
    }

    @ProxyStandard
    @NameToken(NameTokens.LOGIN)
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<LoginPresenter> {
    }

    private final PlaceManager placeManager;
    private final CurrentUser currentUser;
    private final AuthFailurePresenter authFailurePresenter;

    @Inject
    LoginPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            PlaceManager placeManager,
            CurrentUser currentUser,
            AuthFailurePresenter authFailurePresenter) {
        super(eventBus, view, proxy, RevealType.Root);
        this.placeManager = placeManager;
        this.currentUser = currentUser;
        this.authFailurePresenter = authFailurePresenter;
        getView().setUiHandlers(this);
    }

    public void onLogin(String login, String password) {
        if (isLegitimateUser(login, password)) {
            currentUser.setLoggedIn();
            PlaceRequest placeRequest = new PlaceRequest.Builder()
                    .nameToken(NameTokens.HOME)
                    .build();
            placeManager.revealPlace(placeRequest);
        } else {
            showPopupMessage();
        }
    }

    private void showPopupMessage() {
        addToPopupSlot(authFailurePresenter);
    }

    private boolean isLegitimateUser (String login, String password) {
        return login.equals(CurrentUser.TEST_LOGIN) && password.equals(CurrentUser.TEST_PASS);
    }
}