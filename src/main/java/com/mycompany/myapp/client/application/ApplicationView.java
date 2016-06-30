package com.mycompany.myapp.client.application;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiBinder;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, ApplicationView> {
    }

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
