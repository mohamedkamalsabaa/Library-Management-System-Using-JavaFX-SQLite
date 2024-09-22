package com.mks;

public class Books_DB {
    private String title;
    private String author;
    private String genre;
    private int height;
    private String publisher;

    public Books_DB(String title, String author, String genre, int height, String publisher) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.height = height;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getHeight() {
        return height;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
