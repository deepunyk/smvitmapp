package com.xoi.smvitm;

public class FeedItems {

    private String title;
    private String description;
    private String imglink;




    public FeedItems(String title, String description, String imglink) {
        this.title = title;
        this.description = description;
        this.imglink = imglink;


    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImglink() {
        return imglink;
    }

}
