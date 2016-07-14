package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;

import com.google.gwt.dom.client.Style;
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
import com.google.gwt.view.client.*;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.mycompany.myapp.client.application.SimpleDoc;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers>
        implements HomePresenter.MyView {
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
        getUiHandlers().onCreate(title, author, description);
    }

    @UiHandler("deleteDoc")
    public void onDelete(ClickEvent event) {
        SimpleDoc selectedDoc = selectionModel.getSelectedObject();
        getUiHandlers().onDelete(selectedDoc);
    }

    public void displayActionError(String warning) {
        docsActionError.setHTML(warning);
    }

    public void addDocument(SimpleDoc document) {
        clearActionError();
        docsModel.getList().add(document);
        refreshDocuments();
    }

    public void deleteDocument(SimpleDoc document) {
        clearActionError();
        selectionModel.clear();
        docsModel.getList().remove(document);
        refreshDocuments();
    }

    public void refreshDocuments() {
        docsModel.refresh();
    }

    private void initDocsTable() {
        docsTable = new CellTable<SimpleDoc>(new ProvidesKey<SimpleDoc>() {
            public Object getKey(SimpleDoc doc) {
                return (doc == null) ? null : doc.getTitle();
            }
        });
        docsTable.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
            public void onRowCountChange(RowCountChangeEvent event) {
                docsTable.setVisibleRange(new Range(0, event.getNewRowCount()));
            }
        });
        initTableColumns(docsTable);
        initSelectionPolicy(docsTable);
        docsModel.addDataDisplay(docsTable);
    }

    private void initTableColumns(CellTable<SimpleDoc> docsTable) {
        initTitleColumn(docsTable);
        initAuthorColumn(docsTable);
        initDescriptionColumn(docsTable);
        initIDColumn(docsTable);
    }

    private void initSelectionPolicy(CellTable<SimpleDoc> docsTable) {
        docsTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        docsTable.setSelectionModel(selectionModel);
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
        docsTable.setColumnWidth(titleColumn, 15.0, Style.Unit.PCT);
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
        docsTable.setColumnWidth(authorColumn, 15.0, Style.Unit.PCT);
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
        docsTable.setColumnWidth(descriptionColumn, 30.0, Style.Unit.PCT);
    }

    private void initIDColumn(CellTable<SimpleDoc> docsTable) {
        TextColumn<SimpleDoc> idColumn = new TextColumn<SimpleDoc>() {
            @Override
            public String getValue(SimpleDoc document) {
                return document.getID();
            }
        };
        docsTable.addColumn(idColumn, "FNCE ID");
        docsTable.setColumnWidth(idColumn, 40.0, Style.Unit.PCT);
    }

    private void clearActionError() {
        docsActionError.setText("");
    }
}