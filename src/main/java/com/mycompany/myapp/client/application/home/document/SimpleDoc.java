package com.mycompany.myapp.client.application.home.document;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SimpleDoc implements IsSerializable {
    private String title;
    private String author;
    private String description;
    private String ID;

    public SimpleDoc() {
    }

    public SimpleDoc(String title, String author, String description, String ID) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.ID = ID;
    }

    public SimpleDoc(SimpleDoc document) {
        title = document.title;
        author = document.author;
        description = document.description;
        ID = document.ID;
    }

    //similar to copy constructor except for creating a new object
    public void makeIdentical(SimpleDoc document) {
        title = document.title;
        author = document.author;
        description = document.description;
        ID = document.ID;
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