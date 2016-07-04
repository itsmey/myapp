package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
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

    @UiHandler("deleteDoc")
    public void onDelete(ClickEvent event) {
        SimpleDoc selectedDoc = selectionModel.getSelectedObject();
        if (selectedDoc != null) {
            docsModel.getList().remove(selectedDoc);
            docsModel.refresh();
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
        docsTable.addColumn(titleColumn, "Title");
    }

    private void initAuthorColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> authorColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getAuthor();
            }
        };
        docsTable.addColumn(authorColumn, "Author");
    }

    private void initDescriptionColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> descriptionColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getDescription();
            }
        };
        docsTable.addColumn(descriptionColumn, "Description");
    }
}