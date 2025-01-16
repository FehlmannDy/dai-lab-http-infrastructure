package com.example.appdai.repository;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
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
        String query = "SELECT * FROM photocards_for_artist";
        try {
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setId(rs.getInt("photocard_id"));
                photocard.setName(rs.getString("pc_name"));
                photocard.setImageUrl(rs.getString("url"));
                photocard.setType(rs.getString("pc_type_enum"));
                photocard.setActive(true);
                photocard.setArtistId(rs.getInt("artists_id"));
                photocard.setSourceId(rs.getInt("official_sources_id"));
                return photocard;
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : "+e.getMessage());
            return List.of();
        }
    }

    public List<String> getAllPcs() {
        String query = "SELECT pc_name FROM all_photocards";
        try {
            return jdbcTemplate.queryForList(query, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la rereturn jdbcTemplate.queryForList(query, Group.class);quête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public String getArtistGroup(int artistId) {
        String query = "SELECT g.groups_name " +
                "FROM groups g " +
                "WHERE g.groups_id = (SELECT ga.grooups_id FROM groups_artists ga WHERE ga.artists_id = ?)";

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

    public List<Group> getAllGroups(){
        String query = "SELECT * FROM groups";
        try {
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                Group group = new Group();
                group.setGroupsId(rs.getInt("groups_id"));
                group.setGroupsName(rs.getString("groups_name"));
                group.setLabel(rs.getString("label"));
                group.setBeginDate(rs.getDate("begin_date"));
                group.setGender(rs.getString("gender"));
                return group;
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public List<Photocard> getPhotocardsByGroup(int groupId) {
        String query = "SELECT * FROM photocards_for_group WHERE groups_id = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{groupId}, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setId(rs.getInt("photocard_id"));
                photocard.setName(rs.getString("pc_name"));
                photocard.setImageUrl(rs.getString("url"));
                photocard.setType(rs.getString("pc_type"));
                photocard.setActive(rs.getBoolean("proposed"));
                photocard.setArtistId(rs.getInt("artists_id"));
                photocard.setSourceId(rs.getInt("official_sources_id"));
                return photocard;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public List<Artist> getGroupArtists(String groupsName) {
        String query = "SELECT\n" +
                "    g.groups_id,\n" +
                "    g.groups_name,\n" +
                "    g.gender,\n" +
                "    g.begin_date,\n" +
                "    g.disband_date,\n" +
                "    a.artists_id AS artist_id,\n" +
                "    a.stage_name,\n" +
                "    a.birth_date,\n" +
                "    a.active\n" +
                "FROM\n" +
                "    groups g\n" +
                "JOIN\n" +
                "    groups_artists ga ON g.groups_id = ga.groups_id\n" +
                "JOIN\n" +
                "    artists a ON a.artists_id = ga.artists_id\n" +
                "WHERE\n" +
                "    g.groups_name = ?";

        try {
            return jdbcTemplate.query(query, new Object[]{groupsName}, (rs, rowNum) -> {
                Artist artist = new Artist();
                artist.setArtistsId(rs.getInt("artist_id"));
                artist.setStageName(rs.getString("stage_name"));
                artist.setBirthDate(rs.getDate("birth_date"));
                artist.setActive(rs.getBoolean("active"));
                return artist;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            e.printStackTrace(); // Pour plus de détails sur l'erreur
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }
    
}
