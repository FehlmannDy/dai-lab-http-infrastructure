package com.example.appdai.model;

/**
 * Represents a photocard.
 *
 * <p>A photocard is an item with the following attributes:</p>
 * <ul>
 *   <li><b>pc_id</b>: The unique identifier for the photocard.</li>
 *   <li><b>pc_name</b>: The name of the photocard.</li>
 *   <li><b>shop_name</b>: The shop name where the photocard was obtained, or <code>null</code> if not applicable.</li>
 *   <li><b>url</b>: The image URL associated with the photocard.</li>
 *   <li><b>pc_type</b>: The type of photocard (e.g., PCA, POB), represented as a {@link PC_type} enum.</li>
 *   <li><b>artists_id</b>: The unique identifier of the artist associated with the photocard (foreign key).</li>
 *   <li><b>official_sources_id</b>: The unique identifier of the official source associated with the photocard (foreign key).</li>
 *   <li><b>proposed</b>: A boolean indicating whether the photocard was proposed by a user or not.</li>
 * </ul>
 */
public class Photocard {

    private int pc_id;
    private String pc_name;
    private String shop_name;
    private String url;
    private PC_type pc_type;
    private int artists_id;
    private int official_sources_id;
    private boolean proposed;


    /**
     * Default Constructor
     */
    public Photocard() {
    }

    /**
     * Constructor without shopName
     *
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
     *
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

    /**
     * Default constructor
     */

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
     * Provides a string representation of the photocard.
     *
     * @return a string describing the photocard with all its attributes
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
