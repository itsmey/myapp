package com.mycompany.myapp.client.application.home.document;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.myapp.client.application.SimpleDoc;

public interface DocumentServiceAsync {
    void onCreate(SimpleDoc document, AsyncCallback<String> asyncCallback);
    void onDelete(SimpleDoc document, AsyncCallback<Void> asyncCallback);
    void onUpdate(SimpleDoc document, AsyncCallback<Void> asyncCallback);
}