package com.mycompany.myapp.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.mycompany.myapp.client.place.NameTokens;
import com.mycompany.myapp.client.application.ApplicationModule;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule.Builder()
                .defaultPlace(NameTokens.LOGIN)
                .errorPlace(NameTokens.LOGIN)
                .unauthorizedPlace(NameTokens.LOGIN)
                .build());
        install(new ApplicationModule());
    }
}