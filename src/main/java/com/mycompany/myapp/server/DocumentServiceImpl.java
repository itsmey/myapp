package com.mycompany.myapp.server;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.application.SimpleDoc;
import com.mycompany.myapp.client.application.home.document.DocumentService;
import java.util.UUID;

public class DocumentServiceImpl extends RemoteServiceServlet implements DocumentService {
    final private String serverDocClass = "TUzbekovTestingClass1";
    final private String serverDocTitle = "TUzbekovPropertyTitle1";
    final private String serverDocAuthor = "TUzbekovPropertyAuthor1";
    final private String serverDocDescription = "TUzbekovPropertyDescription1";
    final private String serverDocNamePrefix = "TUzbekovDoc";

    public void onCreate(SimpleDoc document) {
        ObjectStore objectStore = LoginServiceImpl.getInstance().getObjectStore();
        LoginServiceImpl.getInstance().pushSubject();
        try {
            Document serverDoc = Factory.Document.createInstance(objectStore, serverDocClass);
            Properties serverDocProps = serverDoc.getProperties();
            serverDocProps.putValue("DocumentTitle", generateDocumentTitle());
            serverDocProps.putValue(serverDocTitle, document.getTitle());
            serverDocProps.putValue(serverDocAuthor, document.getAuthor());
            serverDocProps.putValue(serverDocDescription, document.getDescription());
            serverDoc.save(RefreshMode.NO_REFRESH);
        } finally {
            LoginServiceImpl.getInstance().popSubject();
        }
    }

    private String generateDocumentTitle() {
        long seed = UUID.randomUUID().getMostSignificantBits();
        long suffix = Math.abs(seed) % 10000;
        return serverDocNamePrefix + suffix;
    }
}