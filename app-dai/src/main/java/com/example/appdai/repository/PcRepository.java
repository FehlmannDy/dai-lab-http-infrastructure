package com.example.appdai.repository;

import com.example.appdai.model.Photocard;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String getFirstRecord() {
        String query = "SELECT pc_name FROM photocards LIMIT 1";

        try {
            String result = jdbcTemplate.queryForObject(query, String.class);
            return result != null ? result : "Aucune donnée trouvée";
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
            return "Erreur lors de la requête";
        }
    }

    public List<Photocard> getAllPcsWithType() {
        String query = "SELECT id, pc_name, url, pc_type, proposed, artists_id, official_sources_id FROM photocards";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Photocard photocard = new Photocard();
            photocard.setId(rs.getInt("id"));
            photocard.setName(rs.getString("pc_name"));
            photocard.setImageUrl(rs.getString("url"));
            photocard.setType(rs.getString("pc_type"));
            photocard.setActive(rs.getBoolean("proposed"));
            photocard.setArtistId(rs.getInt("artists_id"));
            photocard.setSourceId(rs.getInt("official_sources_id"));
            return photocard;
        });
    }

    public List<String> getAllPcs() {
        String query = "SELECT pc_name FROM photocards";
        try {
            return jdbcTemplate.queryForList(query, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }
}
