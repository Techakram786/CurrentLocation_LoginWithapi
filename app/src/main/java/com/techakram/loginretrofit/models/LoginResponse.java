package com.techakram.loginretrofit.models;

public class LoginResponse {

    private String error;
    private String message;
    private User user;

    public LoginResponse(String error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public String isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
