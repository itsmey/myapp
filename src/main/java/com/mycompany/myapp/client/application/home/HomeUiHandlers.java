package com.mycompany.myapp.client.application.home;

import com.gwtplatform.mvp.client.UiHandlers;
import com.mycompany.myapp.client.application.SimpleDoc;

public interface HomeUiHandlers extends UiHandlers {
    void onCreate(String title, String author, String description);
    void onDelete(SimpleDoc document);
    void onUpdate(SimpleDoc existingDoc, SimpleDoc uncommittedDoc);
}