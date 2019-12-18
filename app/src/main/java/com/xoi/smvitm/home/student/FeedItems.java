package com.xoi.smvitm.home.student;

public class FeedItems {

    private String title;
    private String description;
    private String imglink;
    private String photographer_name;
    private String blogger_name;


    public FeedItems(String title, String description, String imglink, String photographer_name, String blogger_name) {
        this.title = title;
        this.description = description;
        this.imglink = imglink;
        this.photographer_name = photographer_name;
        this.blogger_name = blogger_name;
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

    public String getPhotographer_name() {
        return photographer_name;
    }

    public String getBlogger_name() {
        return blogger_name;
    }
}
