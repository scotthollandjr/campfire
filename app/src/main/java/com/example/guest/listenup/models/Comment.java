package com.example.guest.listenup.models;

/**
 * Created by Guest on 7/14/16.
 */
public class Comment {
    private String content;
    private String user;

    public Comment() {}

    public Comment(String content, String user) {
        this.content = content;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public String getUser() {
        return user;
    }
}


