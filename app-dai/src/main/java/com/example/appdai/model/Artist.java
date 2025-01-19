/**
 * Artist Class
 * This class is used to represent an artist in the system.
 * An artist has an id, a stage name, a birthdate, an active status and a proposed status.
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

public class Artist {

    private int artists_id;
    private String stage_name;
    private boolean active;
    private boolean proposed;

    /**
     * Default constructor
     */
    public Artist() {
    }

    /**
     * Constructor with all the attributes except the proposed status
     * @param artistsId the id of the artist
     * @param stageName the stage name of the artist
     * @param active the active status of the artist
     */
    public Artist(int artistsId, String stageName, boolean active) {
        this.artists_id = artistsId;
        this.stage_name = stageName;
        this.active = active;
        this.proposed = true;
    }

    // Getters and Setters
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
