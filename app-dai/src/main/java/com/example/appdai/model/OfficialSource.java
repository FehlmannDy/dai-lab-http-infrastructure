/**
 * OfficialSource Class
 * This class is used to represent an official source of information.
 * An official source has an official_sources_id, a title, a version name, a release date and a type.
 * An official source can be an album, an event or any other type of source.
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

public class OfficialSource {

    private int official_sources_id;
    private String title;
    private String version_name;
    private OF_type type;
    private boolean proposed;

    /**
     * Default Constructor
     */
    public OfficialSource() {
    }

    /**
     * Constructor without version_name
     * @param id ID of the official source
     * @param title Title of the official source
     * @param type Type of the official source
     */
    public OfficialSource(int id,String title, OF_type type){
        this.official_sources_id = id;
        this.title = title;
        this.version_name = null;
        this.type = type;
        this.proposed = true;
    }

    /**
     * Constructor with version_name
     * @param id ID of the official source
     * @param title Title of the official source
     * @param versionName Version name of the official source
     * @param type Type of the official source
     */
    public OfficialSource(int id,String title,String versionName, OF_type type){
        this.official_sources_id = id;
        this.title = title;
        this.version_name = versionName;
        this.type = type;
    }

    // Getters and Setters
    public int getOfficial_sources_id() {
        return official_sources_id;
    }
    public void setOfficial_sources_id(int official_sources_id) {
        this.official_sources_id = official_sources_id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion_name() {
        return version_name;
    }
    public void setVersion_name(String version_name) {
        this.version_name = version_name
        ;}

    public OF_type getType() {
        return type;
    }
    public void setType(OF_type type){
        this.type = type;
    }

    public boolean isProposed() {
        return proposed;
    }
    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }
}