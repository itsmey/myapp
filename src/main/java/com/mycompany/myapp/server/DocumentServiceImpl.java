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
import com.mycompany.myapp.client.application.home.document.SimpleDoc;
import com.mycompany.myapp.client.application.home.document.DocumentService;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.collection.IndependentObjectSet;

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
            Id documentID = new Id(uncommittedDoc.getID());
            Document currentDoc = Factory.Document.getInstance(objectStore, serverDocClass, documentID);
            Properties currentDocProperties = currentDoc.getProperties();
            updateEachProperty(currentDocProperties, uncommittedDoc);
            currentDoc.save(RefreshMode.NO_REFRESH);
        } finally {
            LoginServiceImpl.getInstance().popSubject();
        }
    }

    public List<SimpleDoc> onReveal() {
        ObjectStore objectStore = LoginServiceImpl.getInstance().getObjectStore();
        SearchScope scope = new SearchScope(objectStore);
        SearchSQL sqlQuery = new SearchSQL(constructSqlQuery());
        List<SimpleDoc> initialDocs = new ArrayList<>();
        LoginServiceImpl.getInstance().pushSubject();
        try {
            IndependentObjectSet resultSet = scope.fetchObjects(sqlQuery, null, null, false);
            for(Iterator it = resultSet.iterator(); it.hasNext(); ) {
                Document serverDoc = (Document)it.next();
                serverDoc.refresh();
                SimpleDoc doc = reconstructDoc(serverDoc);
                initialDocs.add(doc);
            }
        } finally {
            LoginServiceImpl.getInstance().popSubject();
        }
        return initialDocs;
    }

    private String generateDocumentTitle() {
        long seed = UUID.randomUUID().getMostSignificantBits();
        long suffix = Math.abs(seed) % 10000;
        return serverDocNamePrefix + suffix;
    }

    private void updateEachProperty(Properties properties, SimpleDoc doc) {
        properties.putValue(serverDocTitle, doc.getTitle());
        properties.putValue(serverDocAuthor, doc.getAuthor());
        properties.putValue(serverDocDescription, doc.getDescription());
    }

    private String constructSqlQuery() {
        return "SELECT " + serverDocAuthor + ", " + serverDocTitle + ", " + serverDocDescription +
                " FROM " + serverDocClass;
    }

    private SimpleDoc reconstructDoc(Document serverDoc) {
        Properties serverDocProperties = serverDoc.getProperties();
        String title = serverDocProperties.getStringValue(serverDocTitle);
        String author = serverDocProperties.getStringValue(serverDocAuthor);
        String description = serverDocProperties.getStringValue(serverDocDescription);
        String ID = serverDoc.get_Id().toString();
        return new SimpleDoc(title, author, description, ID);
    }
}