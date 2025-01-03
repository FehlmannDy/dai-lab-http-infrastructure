package com.example.appdai.model;

// Ce serait cool de créer le modèle pour les groupes aussi, Artiste, etc
public class Pc {

    private int id;                 // ID unique de la carte
    private String name;            // Nom de la carte
    private String imageUrl;        // URL de l'image
    private String type;            // Type de la carte (PCA, POB, etc.)
    private boolean isActive;       // Indique si l'artiste sur la PC est actif
    private int artistId;           // ID de l'artiste
    private int sourceId;           // ID de la source officielle

    // Constructeur par défaut
    public Pc() {
    }

    // Constructeur avec paramètres
    public Pc(int id, String name, String imageUrl, String type, boolean isActive, int artistId, int sourceId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.isActive = isActive;
        this.artistId = artistId;
        this.sourceId = sourceId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getParam2() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "PhotoCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                ", isActive=" + isActive +
                ", artistId=" + artistId +
                ", sourceId=" + sourceId +
                '}';
    }
}
