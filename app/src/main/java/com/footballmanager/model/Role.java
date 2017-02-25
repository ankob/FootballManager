package com.footballmanager.model;

/**
 * Created by Andrew on 23-Feb-17.
 */

public class Role {

    private String name;
    private long id;

    public Role(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;

    }
}
