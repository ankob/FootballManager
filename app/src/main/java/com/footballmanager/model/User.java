package com.footballmanager.model;

/**
 * Created by Andrew on 25-Feb-17.
 */

public class User {

    String name;
    Role role;

    static User instance;

    public static User getInstance() {
        return instance;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
