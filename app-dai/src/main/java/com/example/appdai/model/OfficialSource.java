/**
 * OfficialSource Class
 * This class is used to represent an official source of information.
 * An official source has an id, a title, a version name, a release date and a type.
 * An official source can be an album, an event or any other type of source.
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

import java.util.Date;

/**
 * Type Enum
 * This enum is used to represent the type of official source.
 * An official source can be an album, an event or any other type of source.
 */
enum Type {
    ALBUM,
    OTHER,
    EVENT
}

public class OfficialSource {

    private int id;
    private String title;
    private String versionName;
    private Date releaseDate;
    private Type type;
    private boolean proposed;

    /**
     * Default Constructor
     */
    public OfficialSource() {
    }

    /**
     * Constructor without versionName
     * @param id ID of the official source
     * @param title Title of the official source
     * @param releaseDate Release date of the official source
     * @param type Type of the official source
     */
    public OfficialSource(int id,String title,Date releaseDate, Type type){
        this.id = id;
        this.title = title;
        this.versionName = null;
        this.releaseDate = releaseDate;
        this.type = type;
        this.proposed = true;
    }

    /**
     * Constructor with versionName
     * @param id ID of the official source
     * @param title Title of the official source
     * @param versionName Version name of the official source
     * @param releaseDate Release date of the official source
     * @param type Type of the official source
     */
    public OfficialSource(int id,String title,String versionName, Date releaseDate, Type type){
        this.id = id;
        this.title = title;
        this.versionName = versionName;
        this.releaseDate = releaseDate;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getVersionName() {return versionName;}
    public void setVersionName(String versionName) {this.versionName = versionName;}

    public Date getReleaseDate() {return releaseDate;}
    public void setReleaseDate(Date releaseDate) {this.releaseDate = releaseDate;}

    public Type getType() {return type;}
    public void setType(Type type){this.type = type;}

    public boolean isProposed() {return proposed;}
    public void setProposed(boolean proposed) {this.proposed = proposed;}
}