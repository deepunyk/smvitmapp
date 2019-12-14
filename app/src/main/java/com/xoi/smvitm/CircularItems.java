package com.xoi.smvitm;

public class CircularItems {

    private String circular_title;
    private String circular_date;
    private String circular_pdflink;



    public CircularItems(String circular_title, String circular_date, String circular_pdflink) {
        this.circular_title = circular_title;
        this.circular_date = circular_date;
        this.circular_pdflink = circular_pdflink;
    }

    public String getCircular_title() {
        return circular_title;
    }

    public String getCircular_date() {
        return circular_date;
    }

    public String getCircular_pdflink() {
        return circular_pdflink;
    }
}
