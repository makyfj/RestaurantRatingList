package com.example.assignment_4;

public class Restaurant {
    private String name;
    private int rating;
    private String location;

    public Restaurant(String name, int rating, String location){
        this.name = name;
        this.rating = rating;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
