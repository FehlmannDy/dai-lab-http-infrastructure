package com.example.appdai.repository;

import com.example.appdai.model.Pc;
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

    public List<Pc> getAllPcsWithType() {
        String query = "SELECT id, pc_name, url, pc_type, proposed, artists_id, official_sources_id FROM photocards";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Pc pc = new Pc();
            pc.setId(rs.getInt("id"));
            pc.setName(rs.getString("pc_name"));
            pc.setImageUrl(rs.getString("url"));
            pc.setType(rs.getString("pc_type"));
            pc.setActive(rs.getBoolean("proposed"));
            pc.setArtistId(rs.getInt("artists_id"));
            pc.setSourceId(rs.getInt("official_sources_id"));
            return pc;
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
