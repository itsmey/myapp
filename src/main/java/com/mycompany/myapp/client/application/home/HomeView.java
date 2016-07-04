package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtplatform.mvp.client.ViewImpl;
import com.mycompany.myapp.client.application.SimpleDoc;
import java.util.Arrays;
import java.util.List;

public class HomeView extends ViewImpl implements HomePresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, HomeView> {
    }

    @UiField
    TextBox freshTitle;
    @UiField
    TextBox freshAuthor;
    @UiField
    TextBox freshDescription;
    @UiField
    Button createDoc;
    @UiField
    HTML docsActionError;
    @UiField
    Button deleteDoc;
    @UiField(provided = true)
    CellTable<SimpleDoc> docsTable;

    private ListDataProvider<SimpleDoc> docsModel;
    private SingleSelectionModel<SimpleDoc> selectionModel;

    @Inject
    HomeView(Binder uiBinder,
             ListDataProvider<SimpleDoc> docsModel,
             SingleSelectionModel<SimpleDoc> selectionModel) {
        this.docsModel = docsModel;
        this.selectionModel = selectionModel;
        initDocsTable();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("createDoc")
    public void onCreate(ClickEvent event) {
        String title = freshTitle.getText();
        String author = freshAuthor.getText();
        String description = freshDescription.getText();
        if (title.length() == 0 || author.length() == 0 || description.length() == 0) {
            displayActionError("<p><em>All document's fields must be filled!</em></p>");
        } else {
            clearActionError();
            SimpleDoc freshDoc = new SimpleDoc(title, author, description);
            docsModel.getList().add(freshDoc);
            docsModel.refresh();
        }
    }

    @UiHandler("deleteDoc")
    public void onDelete(ClickEvent event) {
        SimpleDoc selectedDoc = selectionModel.getSelectedObject();
        if (selectedDoc != null) {
            clearActionError();
            docsModel.getList().remove(selectedDoc);
            docsModel.refresh();
        } else {
            displayActionError("<p><em>You must select a document to remove!</em></p>");
        }
    }

    private void initDocsTable() {
        docsTable = new CellTable<SimpleDoc>(new ProvidesKey<SimpleDoc>() {
            public Object getKey(SimpleDoc doc) {
                return (doc == null) ? null : doc.getId();
            }
        });
        initTableColumns(docsTable);
        initSelectionPolicy(docsTable);
        docsModel.addDataDisplay(docsTable);
        final List<SimpleDoc> DOCS = Arrays.asList(
                new SimpleDoc("First", "John Doe", "It'a first document here"),
                new SimpleDoc("Second", "Jane Doe", "Time for the second document!"),
                new SimpleDoc("Third", "Stranger", "The third one"));
        for (SimpleDoc doc: DOCS) {
            docsModel.getList().add(doc);
        }
    }

    private void initTableColumns(CellTable<SimpleDoc> docsTable) {
        initIdColumn(docsTable);
        initTitleColumn(docsTable);
        initAuthorColumn(docsTable);
        initDescriptionColumn(docsTable);
    }

    private void initSelectionPolicy(CellTable<SimpleDoc> docsTable) {
        docsTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        docsTable.setSelectionModel(selectionModel);
    }

    private void initIdColumn(CellTable<SimpleDoc> docsTable) {
        TextColumn<SimpleDoc> idColumn = new TextColumn<SimpleDoc>() {
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getId();
            }
        };
        docsTable.addColumn(idColumn, "ID");
    }

    private void initTitleColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> titleColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getTitle();
            }
        };
        titleColumn.setFieldUpdater(new FieldUpdater<SimpleDoc, String>() {
            public void update(int index, SimpleDoc oldDocument, String updatedTitle) {
                oldDocument.setTitle(updatedTitle);
                docsModel.refresh();
            }
        });
        docsTable.addColumn(titleColumn, "Title");
    }

    private void initAuthorColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> authorColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getAuthor();
            }
        };
        authorColumn.setFieldUpdater(new FieldUpdater<SimpleDoc, String>() {
            public void update(int index, SimpleDoc oldDocument, String updatedAuthor) {
                oldDocument.setAuthor(updatedAuthor);
                docsModel.refresh();
            }
        });
        docsTable.addColumn(authorColumn, "Author");
    }

    private void initDescriptionColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> descriptionColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getDescription();
            }
        };
        descriptionColumn.setFieldUpdater(new FieldUpdater<SimpleDoc, String>() {
            public void update(int index, SimpleDoc oldDocument, String updatedDescription) {
                oldDocument.setDescription(updatedDescription);
                docsModel.refresh();
            }
        });
        docsTable.addColumn(descriptionColumn, "Description");
    }

    private void displayActionError(String warning) {
        docsActionError.setHTML(warning);
    }

    private void clearActionError() {
        docsActionError.setText("");
    }
}