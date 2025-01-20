package com.example.appdai.model;

/**
 * Represents an official source.
 *
 * <p>An official source has the following attributes:</p>
 * <ul>
 *   <li><b>official_sources_id</b>: The unique identifier for the official source.</li>
 *   <li><b>title</b>: The title of the official source.</li>
 *   <li><b>version_name</b>: The version name associated with the official source.</li>
 *   <li><b>type</b>: The type of the official source, represented by an {@link OF_type} enum.</li>
 *   <li><b>proposed</b>: A boolean indicating whether the official source was proposed by a user or not.</li>
 * </ul>
 */
public class OfficialSource {

    private int official_sources_id;
    private String title;
    private String version_name;
    private OF_type type;
    private boolean proposed;

    /**
     * Default constructor
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

    /**
     * Getters and Setters
     */

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

    /**
     * Provides a string representation of the official source.
     *
     * @return a string describing the official source with all its attributes
     */
    @Override
    public String toString() {
        return "OfficialSource{" +
                "official_sources_id=" + official_sources_id +
                ", title='" + title + '\'' +
                ", version_name='" + version_name + '\'' +
                ", type=" + type +
                ", proposed=" + proposed +
                '}';
    }
}