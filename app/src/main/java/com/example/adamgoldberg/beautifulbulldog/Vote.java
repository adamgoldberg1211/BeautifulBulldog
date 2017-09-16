package com.example.adamgoldberg.beautifulbulldog;

import io.realm.RealmObject;

/**
 * Created by Adam Goldberg on 9/15/2017.
 */

public class Vote extends RealmObject {
    private User owner;
    private int rating;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
