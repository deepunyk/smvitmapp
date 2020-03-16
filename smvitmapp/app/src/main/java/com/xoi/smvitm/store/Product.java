package com.xoi.smvitm.store;
public class Product {
    private int id;
    private String title;
    private String des;
    private String cat;
    private String price;
    private String date;
    private String imgLink;
    /*private String mobile;
    private String owner;
    private String name;
    private String sem;
    private String section;
    private String email;
    private String branchid;*/

    public Product(int id, String title, String des, String cat, String price,String date,
                   String imgLink/*,String mobile,String owner,String name,String sem,
                   String section,String email,String branchid*/) {

        this.id = id;
        this.title = title;
        this.des = des;
        this.cat = cat;
        this.price = price;
        this.date = date;
        this.imgLink = imgLink;
        /*this.mobile = mobile;
        this.owner = owner;
        this.name = name;
        this.sem = sem;
        this.section = section;
        this.email = email;
        this.branchid = branchid;*/
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDes() { return des; }

    public String getCat() { return cat; }

    public String getPrice() { return price; }

    public String getDate() { return date; }

    public String getImage() { return imgLink; }

/*    public String getMobile() { return mobile; }

    public String getOwner() { return owner; }

    public String getName() { return name; }

    public String getSem() { return sem; }

    public String getSection() { return section; }

    public String getEmail() { return email; }

    public String getBranchid() { return branchid; }*/


}