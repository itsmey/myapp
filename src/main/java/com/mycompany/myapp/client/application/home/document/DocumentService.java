package com.mycompany.myapp.client.application.home.document;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

@RemoteServiceRelativePath("document")
public interface DocumentService extends RemoteService {
    String onCreate(SimpleDoc document);
    void onDelete(SimpleDoc document);
    void onUpdate(SimpleDoc document);
    List<SimpleDoc> onReveal();
}