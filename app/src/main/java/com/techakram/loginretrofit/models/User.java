package com.techakram.loginretrofit.models;

public class User {

    private int id;
    private String email, username;

    public User(int id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;

    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
