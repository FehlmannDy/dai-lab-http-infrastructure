package com.example.appdai.model;

/**
 * Represents an artist.
 *
 * An artist has the following attributes:
 *
 * <ul>
 *   <li><b>artists_id</b>: The unique identifier of the artist.</li>
 *   <li><b>stage_name</b>: The stage name of the artist.</li>
 *   <li><b>active</b>: The active status of the artist, indicating if they are currently active.</li>
 *   <li><b>proposed</b>: A boolean indicating if the artist is proposed by a user or not.</li>
 * </ul>
 */
public class Artist {

    private int artists_id;
    private String stage_name;
    private boolean active;
    private boolean proposed;

    /**
     * Default constructor.
     */
    public Artist() {
    }

    /**
     * Constructs an artist object with the specified attributes, except the proposed status.
     * By default, the proposed status is set to true.
     *
     * @param artistsId the unique identifier of the artist
     * @param stageName the stage name of the artist
     * @param active    the active status of the artist
     */
    public Artist(int artistsId, String stageName, boolean active) {
        this.artists_id = artistsId;
        this.stage_name = stageName;
        this.active = active;
        this.proposed = true;
    }

    /**
     * Getters and Setters
     */

    public int getArtists_id() {
        return artists_id;
    }
    public void setArtists_id(int artists_id) {
        this.artists_id = artists_id;
    }

    public String getStage_name() {
        return stage_name;
    }
    public void setStage_name(String stage_name) {
        this.stage_name = stage_name;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isProposed() {
        return proposed;
    }
    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }

    /**
     * Provides a string representation of the artist.
     *
     * @return a string describing the artist with all its attributes
     */
    @Override
    public String toString() {
        return "Artist{" +
                "artists_id=" + artists_id +
                ", stage_name='" + stage_name + '\'' +
                ", active=" + active +
                ", proposed=" + proposed +
                '}';
    }
}
