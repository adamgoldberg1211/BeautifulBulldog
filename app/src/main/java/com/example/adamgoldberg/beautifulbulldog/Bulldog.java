package com.example.adamgoldberg.beautifulbulldog;

import java.io.Serializable;

/**
 * Created by Adam Goldberg on 9/12/2017.
 */

public class Bulldog implements Serializable {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
