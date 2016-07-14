package com.example.guest.listenup.models;

/**
 * Created by Guest on 7/14/16.
 */
public class Comment {
    private String Content;
    private String User;

    public Comment() {}

    public Comment(String content, String user) {
        this.Content = content;
        this.User = user;
    }

    public String getContent() {
        return Content;
    }

    public String getUser() {
        return User;
    }
}


