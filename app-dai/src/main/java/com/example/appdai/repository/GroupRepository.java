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

/**
 * Repository class for accessing data related to groups and artists in the database.
 * This class interacts directly with the database using JDBC to perform CRUD operations.
 * It provides methods for retrieving group information and related artists from the database.
 *
 * <p>It contains methods to:
 * 1. Get a list of artists by a group ID.
 * 2. Get all groups that have not been proposed yet.
 * 3. Get artists related to a specific group by group name.
 * </p>
 *
 * The class uses {@link JdbcTemplate} for executing SQL queries and handling database connections.
 */
@Repository
public class GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code GroupRepository} instance.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to execute SQL queries.
     */
    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of artists associated with a specific group ID.
     *
     * @param groupId the ID of the group whose artists are to be retrieved.
     * @return a list of maps where each map represents an artist with their ID and stage name.
     * @throws DataAccessException if an error occurs while querying the database.
     */
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

    /**
     * Retrieves all groups that are not marked as "proposed".
     *
     * @return a list of {@link Group} objects representing all non-proposed groups.
     */
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
            return List.of();
        }
    }

    /**
     * Retrieves a list of artists associated with a specific group name.
     *
     * @param groupsName the name of the group whose artists are to be retrieved.
     * @return a list of {@link Artist} objects representing the artists in the specified group.
     */
    public List<Artist> getGroupArtists(String groupsName) {
        String query = "SELECT  a.artists_id AS artist_id, a.stage_name, a.active\n" +
                "FROM groups g\n" +
                "JOIN groups_artists ga ON g.groups_id = ga.groups_id\n" +
                "JOIN artists a ON a.artists_id = ga.artists_id\n" +
                "WHERE g.groups_name = ?";
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
            return List.of();
        }
    }

    public void addGroup(Group group) {
        String query = "INSERT INTO groups (groups_name,gender,proposed) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(connection ->{
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, group.getGroups_name());
                stmt.setString(2, group.getGender());
                stmt.setBoolean(3, true);
                return stmt;
            });
        }catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }


    public boolean validGroup(int groupId, boolean proposed){
        if(proposed) {
            String query = "UPDATE groups SET proposed = TRUE WHERE groups_id = ?";

            try {
                int updateRows = jdbcTemplate.update(query, groupId);
                return updateRows > 0;
            } catch (DataAccessException e) {
                System.err.println("Erreur de la validité du group : " + e.getMessage());
                return false;
            }
        }else{
            String query = "DELETE official_sources WHERE id = ?";

            try {
                int updateRows = jdbcTemplate.update(query, groupId);
                return updateRows > 0;
            } catch (DataAccessException e) {
                System.err.println("Erreur de la validité du group : " + e.getMessage());
                return false;
            }
        }
    }
}