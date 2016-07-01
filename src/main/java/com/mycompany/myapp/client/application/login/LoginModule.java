package com.mycompany.myapp.client.application.login;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.mycompany.myapp.client.application.login.authfailure.AuthFailurePresenter;
import com.mycompany.myapp.client.application.login.authfailure.AuthFailureView;

public class LoginModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LoginPresenter.class,
                LoginPresenter.MyView.class,
                LoginView.class,
                LoginPresenter.MyProxy.class);
        bindSingletonPresenterWidget(AuthFailurePresenter.class,
                AuthFailurePresenter.MyView.class,
                AuthFailureView.class);
    }
}