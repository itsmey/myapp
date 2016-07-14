package com.mycompany.myapp.server;

import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.util.Id;
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

    public String onCreate(SimpleDoc document) {
        ObjectStore objectStore = LoginServiceImpl.getInstance().getObjectStore();
        LoginServiceImpl.getInstance().pushSubject();
        try {
            Document serverDoc = Factory.Document.createInstance(objectStore, serverDocClass);
            Properties serverDocProps = serverDoc.getProperties();
            serverDocProps.putValue("DocumentTitle", generateDocumentTitle());
            serverDocProps.putValue(serverDocTitle, document.getTitle());
            serverDocProps.putValue(serverDocAuthor, document.getAuthor());
            serverDocProps.putValue(serverDocDescription, document.getDescription());
            serverDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            serverDoc.save(RefreshMode.REFRESH);
            return serverDoc.get_Id().toString();
        } finally {
            LoginServiceImpl.getInstance().popSubject();
        }
    }

    public void onDelete(SimpleDoc document) {
        ObjectStore objectStore = LoginServiceImpl.getInstance().getObjectStore();
        LoginServiceImpl.getInstance().pushSubject();
        try {
            String documentID = document.getID();
            Document serverDoc = Factory.Document.getInstance(objectStore, serverDocClass, new Id(documentID));
            serverDoc.delete();
            serverDoc.save(RefreshMode.NO_REFRESH);
        } finally {
            LoginServiceImpl.getInstance().popSubject();
        }
    }

    public void onUpdate(SimpleDoc uncommittedDoc) {
        ObjectStore objectStore = LoginServiceImpl.getInstance().getObjectStore();
        LoginServiceImpl.getInstance().pushSubject();
        try {
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