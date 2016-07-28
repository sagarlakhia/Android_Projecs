package com.example.layoutdemo;

/**
 * Created by Sagar on 22-07-2016.
 */
public class AlbumItem {

    private int albumID;
    private int albumThumbnail;
    private String albumTitle;

    public AlbumItem(int albumID,int albumThumbnail, String albumTitle)
    {
        super();
        this.albumID=albumID;
        this.albumThumbnail=albumThumbnail;
        this.albumTitle=albumTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public int getAlbumThumbnail() {
        return albumThumbnail;
    }

    public int getAlbumID() {
        return albumID;
    }


}
