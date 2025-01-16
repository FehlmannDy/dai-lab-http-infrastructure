package com.example.appdai.model;

import java.util.Date;

enum Type {
    ALBUM,
    OTHER,
    EVENT,
    BENEFIT
}

public class OfficialSource {

    private int id;
    private String title;
    private String versionName;
    private String shopName;
    private Date releaseDate;
    private Type type;

    public OfficialSource(int id,String title,Date releaseDate, Type type){
        this.id = id;
        this.title = title;
        this.versionName = null;
        this.shopName = null;
        this.releaseDate = releaseDate;
        this.type = type;
    }

    public OfficialSource(int id,String title, String versionName, String shopName, Date releaseDate, Type type){
        this.id = id;
        this.title = title;
        this.versionName = versionName;
        this.shopName = shopName;
        this.releaseDate = releaseDate;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }
}