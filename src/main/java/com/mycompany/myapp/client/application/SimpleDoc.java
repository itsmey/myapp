package com.mycompany.myapp.client.application;

import java.util.Date;

public class SimpleDoc {
    private static int idCounter;
    private final int id;
    private final String title;
    private final String author;
    private final Date creationDate;

    public SimpleDoc (String title, String author, Date creationDate) {
        this.id = ++idCounter;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
    }

    public SimpleDoc() {
        this("unknown document", "unknown author", new Date(System.currentTimeMillis()));
    }

    public String getId() {
        return Integer.toString(id);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }
}