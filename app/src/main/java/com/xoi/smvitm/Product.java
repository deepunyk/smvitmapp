package com.xoi.smvitm;
public class Product {
    private int id;
    private String title;
    private String des;
    private String cat;
    private String price;
    private String date;
    private String image;

    public Product(int id, String title, String des, String cat, String price,String date, String image) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.cat = cat;
        this.price = price;
        this.date = date;
        this.image = image;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDes() { return des; }

    public String getCat() { return cat; }

    public String getPrice() { return price; }

    public String getDate() { return date; }

    public String getImage() { return image; }
}