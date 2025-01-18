/**
 * Photocard Class
 * This class is used to create Photocard objects
 * A Photocard is composed of an ID, a name, a shopName, an image URL, a type, an artist ID and a source ID
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

public class Photocard {

    private int id;                 // ID unique de la carte
    private String name;            // Nom de la carte
    private String shopName;        // if not null = Benefit
    private String imageUrl;        // URL de l'image
    private PC_type type;            // Type de la carte (PCA, POB, etc.)
    private int artistId;           // ID de l'artiste (FK)
    private int sourceId;           // ID de la source officielle (FK)
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
        this.id = id;
        this.name = name;
        this.shopName = null;
        this.imageUrl = imageUrl;
        this.type = type;
        this.artistId = artistId;
        this.sourceId = sourceId;
        this.proposed = true;
    }

    /**
     * Constructor with shopName
     * @param id ID of the Photocard
     * @param name Name of the Photocard
     * @param shopName Shop name of the Photocard
     * @param imageUrl Image URL of the Photocard
     * @param type Type of the Photocard
     * @param artistId ID of the artist (FK)
     * @param sourceId ID of the official source (FK)
     */
    public Photocard(int id, String name, String shopName, String imageUrl, PC_type type, int artistId, int sourceId) {
        this.id = id;
        this.name = name;
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.type = type;
        this.artistId = artistId;
        this.sourceId = sourceId;
        this.proposed = true;
    }

    // Getters et Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getShopName() {return shopName;}
    public void setShopName(String shopName) {this.shopName = shopName;}

    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public PC_type getType() {return type;}
    public void setType(PC_type type) {this.type = type;}

    public int getArtistId() {return artistId;}
    public void setArtistId(int artistId) {this.artistId = artistId;}

    public int getSourceId() {return sourceId;}
    public void setSourceId(int sourceId) {this.sourceId = sourceId;}

    public boolean isProposed() {return proposed;}
    public void setProposed(boolean proposed) {this.proposed = proposed;}

    /**
     * toString Method
     * This method is used to display the Photocard object
     * @return String
     */
    @Override
    public String toString() {
        return "PhotoCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shopName='" + shopName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                ", artistId=" + artistId +
                ", sourceId=" + sourceId +
                '}';
    }
}
