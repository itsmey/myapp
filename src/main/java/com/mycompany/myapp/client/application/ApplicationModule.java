package com.mycompany.myapp.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.mycompany.myapp.client.application.login.LoginModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class,
                ApplicationPresenter.MyView.class,
                ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
        install(new LoginModule());
    }
}