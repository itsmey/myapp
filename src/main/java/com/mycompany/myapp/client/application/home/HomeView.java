package com.mycompany.myapp.client.application.home;

import javax.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
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
        docsTable = new CellTable<SimpleDoc>(new ProvidesKey<SimpleDoc>() {
            public Object getKey(SimpleDoc doc) {
                return (doc == null) ? null : doc.getId();
            }
        });
        initTableColumns(docsTable);
        initSelectionPolicy(docsTable);
        listDataProvider = new ListDataProvider<SimpleDoc>();
        listDataProvider.addDataDisplay(docsTable);
        final List<SimpleDoc> DOCS = Arrays.asList(new SimpleDoc(),
                new SimpleDoc(),
                new SimpleDoc());
        docsTable.setRowCount(DOCS.size(), true);
        docsTable.setRowData(DOCS);
    }

    private void initTableColumns(CellTable<SimpleDoc> docsTable) {
        initIdColumn(docsTable);
        initTitleColumn(docsTable);
        initAuthorColumn(docsTable);
        initDateColumn(docsTable);
    }

    private void initSelectionPolicy(CellTable<SimpleDoc> docsTable) {
        docsTable.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        final SingleSelectionModel<SimpleDoc> selectionModel = new SingleSelectionModel<SimpleDoc>();
        docsTable.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                SimpleDoc selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    Window.alert("You selected: " + selected.getId());
                }
            }
        });
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

    private void initDateColumn(CellTable<SimpleDoc> docsTable) {
        Column<SimpleDoc, String> dateColumn = new Column<SimpleDoc, String>(new EditTextCell()){
            @Override
            public String getValue(SimpleDoc contact) {
                return contact.getCreationDate();
            }
        };
        docsTable.addColumn(dateColumn, "Date of creation");
    }
}