package com.mycompany.myapp.client.application.home.document;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.myapp.client.application.SimpleDoc;

@RemoteServiceRelativePath("document")
public interface DocumentService extends RemoteService {
    void onCreate(SimpleDoc document);
}