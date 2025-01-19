/**
 * Photocard Class
 * This class is used to create Photocard objects
 * A Photocard is composed of an ID, a pc_name, a shopName, an image URL, a pc_type, an artist ID and a source ID
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

public class Photocard {

    private int pc_id;                 // ID unique de la carte
    private String pc_name;            // Nom de la carte
    private String shop_name;        // if not null = Benefit
    private String url;        // URL de l'image
    private PC_type pc_type;            // Type de la carte (PCA, POB, etc.)
    private int artists_id;           // ID de l'artiste (FK)
    private int official_sources_id;           // ID de la source officielle (FK)
    private boolean proposed;       // Indique si la carte est proposée


    /**
     * Default Constructor
     */
    public Photocard() {
    }

    /**
     * Constructor without shopName
     * @param id ID of the Photocard
     * @param name Name of the Photocard
     * @param imageUrl Image URL of the Photocard
     * @param type Type of the Photocard
     * @param artistId ID of the artist (FK)
     * @param sourceId ID of the official source (FK)
     */
    public Photocard(int id, String name, String imageUrl, PC_type type, int artistId, int sourceId) {
        this.pc_id = id;
        this.pc_name = name;
        this.shop_name = null;
        this.url = imageUrl;
        this.pc_type = type;
        this.artists_id = artistId;
        this.official_sources_id = sourceId;
        this.proposed = true;
    }

    /**
     * Constructor with shopName
     * @param id ID of the Photocard
     * @param name Name of the Photocard
     * @param shopName Shop pc_name of the Photocard
     * @param imageUrl Image URL of the Photocard
     * @param type Type of the Photocard
     * @param artistId ID of the artist (FK)
     * @param sourceId ID of the official source (FK)
     */
    public Photocard(int id, String name, String shopName, String imageUrl, PC_type type, int artistId, int sourceId) {
        this.pc_id = id;
        this.pc_name = name;
        this.shop_name = shopName;
        this.url = imageUrl;
        this.pc_type = type;
        this.artists_id = artistId;
        this.official_sources_id = sourceId;
        this.proposed = true;
    }

    // Getters et Setters
    public int getPc_id() {
        return pc_id;
    }
    public void setPc_id(int pc_id) {
        this.pc_id = pc_id;
    }

    public String getPc_name() {
        return pc_name;
    }
    public void setPc_name(String pc_name) {
        this.pc_name = pc_name;
    }

    public String getShop_name() {
        return shop_name;
    }
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public PC_type getPc_type() {
        return pc_type;
    }
    public void setPc_type(PC_type pc_type) {
        this.pc_type = pc_type;
    }

    public int getArtists_id() {
        return artists_id;
    }
    public void setArtists_id(int artists_id) {
        this.artists_id = artists_id;
    }

    public int getOfficial_sources_id() {
        return official_sources_id;
    }
    public void setOfficial_sources_id(int official_sources_id) {
        this.official_sources_id = official_sources_id;
    }

    public boolean isProposed() {
        return proposed;
    }
    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }

    /**
     * toString Method
     * This method is used to display the Photocard object
     * @return String
     */
    @Override
    public String toString() {
        return "PhotoCard{" +
                "pc_id=" + pc_id +
                ", pc_name='" + pc_name + '\'' +
                ", shopName='" + shop_name + '\'' +
                ", url='" + url + '\'' +
                ", pc_type='" + pc_type + '\'' +
                ", artists_id=" + artists_id +
                ", official_sources_id=" + official_sources_id +
                '}';
    }
}
