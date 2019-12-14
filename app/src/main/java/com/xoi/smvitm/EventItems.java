package com.xoi.smvitm;

public class EventItems {

    private String event_title;
    private String event_description;
    private String event_organizers;
    private String event_date;
    private String event_imglink;
    private String event_pdflink;


    public EventItems(String event_title, String event_description, String event_organizers, String event_date, String event_imglink, String event_pdflink) {
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_organizers = event_organizers;
        this.event_date = event_date;
        this.event_imglink = event_imglink;
        this.event_pdflink = event_pdflink;
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
