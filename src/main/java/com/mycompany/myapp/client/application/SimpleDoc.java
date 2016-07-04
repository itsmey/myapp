package com.mycompany.myapp.client.application;

public class SimpleDoc {
    private static int idCounter;
    private final int id;
    private String title;
    private String author;
    private String description;

    public SimpleDoc (String title, String author, String description) {
        this.id = ++idCounter;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public SimpleDoc() {
        this("unknown document", "unknown author", "default document");
    }

    public String getId() {
        return Integer.toString(id);
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
}