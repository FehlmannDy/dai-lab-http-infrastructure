package com.example.appdai.repository;

import com.example.appdai.model.PC_type;
import com.example.appdai.model.Photocard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
Le package repository contient :

    Les classes d'accès aux données (via JDBC).
    Ces classes interagissent directement avec ta base de données pour créer,
    lire, mettre à jour ou supprimer des données (CRUD).

    En bas y a des exemples mais faudra faire des prepared statements
 */
@Repository
public class PcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Avoir une vue peut etre ici car on aimerait pas retourner le artist id et source id mais les noms associés
    // genre une vue qui retourne pc_id, pc_name, shop_name, image_url, type, artist_name, source_name
    // ou une vue qui fait ça mais qui retourne deja les proposed photocards en plus
    public List<Photocard> getProposedPhotocards() {
        String query = "SELECT pc_id, pc_name, shop_name, url, pc_type, artists_id, official_sources_id FROM photocards WHERE proposed = TRUE";

        try {
            return jdbcTemplate.query(query, (rs, rowNum) -> new Photocard(
                    rs.getInt("pc_id"),
                    rs.getString("pc_name"),
                    rs.getString("shop_name"),
                    rs.getString("url"),
                    PC_type.valueOf(rs.getString("pc_type")),
                    rs.getInt("artists_id"),
                    rs.getInt("official_sources_id")
            ));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while getting proposed photocards: " + e.getMessage());
        }
    }

    public List<Photocard> getAllPcsWithType() {
        String query = "SELECT * FROM photocards_for_artist";
        try {
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setPc_id(rs.getInt("photocard_id"));
                photocard.setPc_name(rs.getString("pc_name"));
                photocard.setShop_name(rs.getString("shop_name"));
                photocard.setUrl(rs.getString("url"));
                photocard.setPc_type(PC_type.valueOf(rs.getString("pc_type")));
                photocard.setArtists_id(rs.getInt("artists_id"));
                photocard.setOfficial_sources_id(rs.getInt("official_sources_id"));
                return photocard;
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : "+e.getMessage());
            return List.of();
        }
    }

    public List<String> getAllPcs() {
        String query = "SELECT pc_name FROM photocards WHERE pc_id < 15";
        try {
            return jdbcTemplate.queryForList(query, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la return jdbcTemplate.queryForList(query, Group.class);quête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public String getArtistGroup(int artistId) {

        String query = "SELECT g.groups_name " +
                "FROM groups g " +
                "WHERE g.groups_id = (SELECT ga.groups_id FROM groups_artists ga WHERE ga.artists_id = ?)";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{artistId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Aucun groupe trouvé pour l'artiste avec l'ID : " + artistId);
            return null; // Ou retournez une chaîne par défaut, ex. "Inconnu"
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return null;
        }
    }

    public List<Photocard> getPhotocardsByGroup(int groupId) {
        String query = "SELECT * FROM photocards_for_group WHERE groups_id = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{groupId}, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setPc_id(rs.getInt("photocard_id"));
                photocard.setPc_name(rs.getString("pc_name"));
                photocard.setShop_name(rs.getString("shop_name"));
                photocard.setUrl(rs.getString("url"));
                photocard.setPc_type(PC_type.valueOf(rs.getString("pc_type")));
                photocard.setArtists_id(rs.getInt("artists_id"));
                photocard.setOfficial_sources_id(rs.getInt("official_sources_id"));
                return photocard;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }
}
