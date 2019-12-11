package com.xoi.smvitm;

public class CircularItems {
    private String circular_title;
    private String circular_date;
    private String circular_pdflink;
    private String event_title;
    private String event_description;
    private String event_organizers;
    private String event_date;
    private String event_imglink;
    private String event_pdflink;

    public CircularItems(
            String circular_title, String circular_date, String circular_pdflink,
            String event_title, String event_description, String event_organizers,
            String event_date, String event_imglink, String event_pdflink) {

        this.circular_title = circular_title;
        this.circular_date = circular_date;
        this.circular_pdflink = circular_pdflink;
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_organizers = event_organizers;
        this.event_date = event_date;
        this.event_imglink = event_imglink;
        this.event_pdflink = event_pdflink;
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

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_organizers() {
        return event_organizers;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_imglink() {
        return event_imglink;
    }

    public String getEvent_pdflink() {
        return event_pdflink;
    }
}
