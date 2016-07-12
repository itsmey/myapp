package com.mycompany.myapp.client.application;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SimpleDoc implements IsSerializable {
    private String title;
    private String author;
    private String description;
    private String ID;

    public SimpleDoc() {
        this("sample document", "unknown author", "empty description");
    }

    public SimpleDoc (String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}