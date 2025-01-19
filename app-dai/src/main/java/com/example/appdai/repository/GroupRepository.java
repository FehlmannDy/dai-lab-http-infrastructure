package com.example.appdai.repository;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Le package repository contient :

    Les classes d'accès aux données (via JDBC).
    Ces classes interagissent directement avec ta base de données pour créer,
    lire, mettre à jour ou supprimer des données (CRUD).

    En bas y a des exemples mais faudra faire des prepared statements
 */
@Repository
public class GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getArtistsByGroupId(int groupId) {
        String query = "SELECT a.artists_id, a.stage_name " +
                "FROM artists a " +
                "JOIN groups_artists ga ON a.artists_id = ga.artists_id " +
                "JOIN groups g ON g.groups_id = ga.groups_id " +
                "WHERE g.groups_id = ?";

        return jdbcTemplate.execute((Connection connection) -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, groupId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<Map<String, Object>> artists = new ArrayList<>();

                    while (resultSet.next()) {
                        Map<String, Object> artist = new HashMap<>();
                        artist.put("artists_id", resultSet.getInt("artists_id"));
                        artist.put("stage_name", resultSet.getString("stage_name"));
                        artists.add(artist);
                    }
                    return artists;
                }
            }
        });
    }

    public List<Group> getAllGroups(){
        String query = "SELECT * FROM groups WHERE proposed = FALSE";
        try {
            return jdbcTemplate.query(query, (rs, rowNum) -> {
                Group group = new Group();
                group.setGroups_id(rs.getInt("groups_id"));
                group.setGroups_name(rs.getString("groups_name"));
                return group;
            });
        } catch (Exception e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
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
                artist.setArtists_id(rs.getInt("artist_id"));
                artist.setStage_name(rs.getString("stage_name"));
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