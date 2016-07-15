package com.mycompany.myapp.client.application.home.document;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

public interface DocumentServiceAsync {
    void onCreate(SimpleDoc document, AsyncCallback<String> asyncCallback);
    void onDelete(SimpleDoc document, AsyncCallback<Void> asyncCallback);
    void onUpdate(SimpleDoc document, AsyncCallback<Void> asyncCallback);
    void onReveal(AsyncCallback<List<SimpleDoc>> asyncCallback);
}