package com.example.guest.campfire.models;


public class Market {
    private String address;
    private String link;
    private String products;
    private String schedule;

    public Market(String address, String link, String products, String schedule) {
        this.address = address;
        this.link = link;
        this.products = products;
        this.schedule = schedule;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public String getProducts() {
        return products;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
