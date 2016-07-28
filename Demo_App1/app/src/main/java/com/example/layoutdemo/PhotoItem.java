package com.example.layoutdemo;

/**
 * Created by Sagar on 22-07-2016.
 */
public class PhotoItem  {

    private String url;
    private String title;
    private String thumbnailUrl;
    private int id;
    public PhotoItem(String url, String title,String thumbnailUrl,int id){
        super();
        this.url=url;
        this.title=title;
        this.thumbnailUrl=thumbnailUrl;
        this.id=id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }




}
