package com.example.adamgoldberg.beautifulbulldog;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Adam Goldberg on 9/15/2017.
 */

public class User extends RealmObject implements Serializable {
    @PrimaryKey
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
