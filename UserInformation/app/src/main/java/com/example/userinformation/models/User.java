package com.example.userinformation.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sagar on 17-02-2017.
 */

public class User {

    @SerializedName("name")
    public String name;
    @SerializedName("age")
    public int age;
    @SerializedName("email")
    public String email;
    @SerializedName("profession")
    public String profession;
}
