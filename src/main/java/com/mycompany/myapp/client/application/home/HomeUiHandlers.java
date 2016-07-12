package com.mycompany.myapp.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;

public interface HomeUiHandlers extends UiHandlers {
    void onCreate(String title, String author, String description);
}