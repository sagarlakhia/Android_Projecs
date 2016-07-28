package com.example.layoutdemo;

/**
 * Created by Sagar on 21-07-2016.
 */
public class UserItem {

    private int idN;
    private int thumbnail;
    private String name;
    private String email;


    public UserItem(int idN, int thumbnail, String name,String email)
    {
        super();
        this.idN=idN;
        this.thumbnail=thumbnail;
        this.name=name;
        this.email=email;
    }
    public int getIdN() {
        return idN;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getThumbnail() {
        return thumbnail;
    }

}
