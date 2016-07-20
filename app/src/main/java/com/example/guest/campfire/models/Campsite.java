package com.example.guest.campfire.models;

/**
 * Created by Guest on 7/14/16.
 */
public class Campsite {
    private String content;
    private String user;

    public Campsite() {}

    public Campsite(String content, String user) {
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


