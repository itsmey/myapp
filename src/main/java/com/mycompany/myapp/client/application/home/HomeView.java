package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.gwtplatform.mvp.client.ViewImpl;
import com.mycompany.myapp.client.application.SimpleDoc;

import java.util.Arrays;
import java.util.List;

public class HomeView extends ViewImpl implements HomePresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, HomeView> {
    }

    @UiField(provided = true)
    CellTable<SimpleDoc> docsTable;

    private ListDataProvider<SimpleDoc> listDataProvider;

    @Inject
    HomeView(Binder uiBinder) {
        initDocsTable();
        initWidget(uiBinder.createAndBindUi(this));
    }

    private void initDocsTable() {
        ProvidesKey<SimpleDoc> keyProvider = new ProvidesKey<SimpleDoc>() {
            public Object getKey(SimpleDoc doc) {
                return (doc == null) ? null : doc.getId();
            }
        };
        docsTable = new CellTable<SimpleDoc>(keyProvider);
        initIdColumn();
        initTitleColumn();
        initAuthorColumn();
        initDateColumn();
        listDataProvider = new ListDataProvider<SimpleDoc>();
        listDataProvider.addDataDisplay(docsTable);
        final List<SimpleDoc> DOCS = Arrays.asList(new SimpleDoc(),
                new SimpleDoc(),
                new SimpleDoc());
        docsTable.setRowCount(DOCS.size(), true);
        docsTable.setRowData(DOCS);
    }

    private void initIdColumn() {
        TextColumn<SimpleDoc> idColumn = new TextColumn<SimpleDoc>() {
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getId();
            }
        };
        docsTable.addColumn(idColumn, "ID");
    }

    private void initTitleColumn() {
        Column<SimpleDoc, String> titleColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getTitle();
            }
        };
        docsTable.addColumn(titleColumn, "Title");
    }

    private void initAuthorColumn() {
        Column<SimpleDoc, String> authorColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getAuthor();
            }
        };
        docsTable.addColumn(authorColumn, "Author");
    }

    private void initDateColumn() {
        Column<SimpleDoc, String> dateColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getCreationDate();
            }
        };
        docsTable.addColumn(dateColumn, "Date of creation");
    }
}