package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.mycompany.myapp.client.application.CurrentUser;
import com.mycompany.myapp.client.application.login.LoginService;
import com.mycompany.myapp.client.application.login.LoginServiceAsync;
import com.mycompany.myapp.client.place.NameTokens;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
        implements HomeUiHandlers {
    interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    private CurrentUser currentUser;
    private PlaceManager placeManager;

    @Inject
    HomePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            CurrentUser currentUser,
            PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);
        this.currentUser = currentUser;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    public void onLogout() {
        LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);
        AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                //TODO: add proper error handling
            }

            public void onSuccess(Void result) {
                showLoginPage();
            }
        };
        loginServiceAsync.logoutUser(asyncCallback);
    }

    private void showLoginPage() {
        currentUser.setLoggedOut();
        PlaceRequest placeRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.LOGIN)
                .build();
        placeManager.revealPlace(placeRequest);
    }
}