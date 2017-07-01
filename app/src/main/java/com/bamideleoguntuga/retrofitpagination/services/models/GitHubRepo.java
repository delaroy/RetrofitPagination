package com.bamideleoguntuga.retrofitpagination.services.models;

public class GitHubRepo {

    private int id;
    private String name;

    public GitHubRepo() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
