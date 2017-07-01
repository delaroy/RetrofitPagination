package com.bamideleoguntuga.retrofitpagination.services.models;

/**
 * Created by delaroy on 6/30/17.
 */
public class PaginationItem {

    private int id;
    private String message;

    public PaginationItem(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}