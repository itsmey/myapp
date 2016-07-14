package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.mycompany.myapp.client.application.SimpleDoc;
import com.mycompany.myapp.client.application.home.document.DocumentService;
import com.mycompany.myapp.client.application.home.document.DocumentServiceAsync;
import com.mycompany.myapp.client.place.NameTokens;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
        implements HomeUiHandlers {
    interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
        void displayActionError(String warning);
        void addDocument(SimpleDoc document);
        void deleteDocument(SimpleDoc document);
        void refreshDocuments();
    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    HomePresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        getView().setUiHandlers(this);
    }

    public void onCreate(String title, String author, String description) {
        if (!isValidDoc (title, author, description)) {
            displayActionError("All document's fields must be filled!");
        } else {
            final SimpleDoc serverDoc = new SimpleDoc(title, author, description);
            DocumentServiceAsync documentServiceAsync = GWT.create(DocumentService.class);
            AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    displayActionError(caught.getCause().toString());
                }

                public void onSuccess(String documentID) {
                    serverDoc.setID(documentID);
                    getView().addDocument(serverDoc);
                }
            };
            documentServiceAsync.onCreate(serverDoc, asyncCallback);
        }
    }

    public void onDelete(final SimpleDoc document) {
        if (document == null) {
            displayActionError("You must select a document to remove!");
        } else {
            DocumentServiceAsync documentServiceAsync = GWT.create(DocumentService.class);
            AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    displayActionError(caught.getCause().toString());
                }

                @Override
                public void onSuccess(Void result) {
                    getView().deleteDocument(document);
                }
            };
            documentServiceAsync.onDelete(document, asyncCallback);
        }
    }

    public void onUpdate(SimpleDoc oldDocument, SimpleDoc uncommittedDocument) {
        oldDocument.makeIdentical(uncommittedDocument);
        getView().refreshDocuments();
    }

    private void displayActionError(String warning) {
        getView().displayActionError("<p><em>Error: " + warning + "</em></p>");
    }

    private boolean isValidDoc(String title, String author, String description) {
        return !title.isEmpty() && !author.isEmpty() && !description.isEmpty();
    }
}