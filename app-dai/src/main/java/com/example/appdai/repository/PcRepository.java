package com.example.appdai.repository;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.PC_type;
import com.example.appdai.model.Photocard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing photocards in the database.
 * This class interacts with the database using JDBC to retrieve and manipulate data related to photocards.
 *
 * <p>It provides methods to fetch photocards, paginate them, retrieve photocards by group, and get related data like artist and group names.</p>
 *
 * The class uses {@link JdbcTemplate} for executing SQL queries and managing database connections.
 */
@Repository
public class PcRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code PcRepository} instance.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to execute SQL queries.
     */
    @Autowired
    public PcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all proposed photocards from the database.
     *
     * @return a list of proposed photocards.
     * @throws RuntimeException if there is an error while fetching the proposed photocards.
     */
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

//    public List<Map<String, Object>> getPaginatedPcs(int offset, int limit) {
//        String query = "SELECT pc_id, pc_name, url, pc_type, artists_id FROM photocards ORDER BY pc_id LIMIT ? OFFSET ?";
//
//        return jdbcTemplate.queryForList(query, limit, offset);
//    }

    /**
     * Retrieves a paginated list of photocards, optionally filtered by a specific group ID.
     *
     * @param groupId the group ID to filter by (can be {@code null} for no filtering).
     * @param page the page number (starting from 1).
     * @param size the number of photocards per page.
     * @return a list of photocards matching the given filters and pagination parameters.
     */

    /**
     * Retrieves a paginated list of photocards, optionally filtered by a specific group ID.
     *
     * @param groupId the group ID to filter by (can be {@code null} for no filtering).
     * @param page the page number (starting from 1).
     * @param size the number of photocards per page.
     * @return a list of photocards matching the given filters and pagination parameters.
     */
public List<Map<String, Object>> getPaginatedPcs(Integer groupId, Integer page, Integer size) {

    System.out.println("Received params: groupId=" + groupId + ", page=" + page + ", size=" + size);

    int pageNumber = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size <= 0) ? 24 : size;
    int pageOffset = (pageNumber - 1) * pageSize;

    String sql = """
    SELECT 
        p.pc_id, p.pc_name, p.url, p.pc_type, 
        a.stage_name AS artist_name, 
        g.groups_id AS group_id, g.groups_name AS group_name 
    FROM photocards p 
    JOIN artists a ON p.artists_id = a.artists_id 
    JOIN groups_artists ga ON a.artists_id = ga.artists_id 
    JOIN groups g ON ga.groups_id = g.groups_id 
    """ + (groupId != null ? "WHERE g.groups_id = ? " : "")
            + "ORDER BY p.pc_id LIMIT ? OFFSET ?";

    List<Object> params = new ArrayList<>();
    if (groupId != null) {
        params.add(groupId);
    }
    params.add(pageSize);
    params.add(pageOffset);

    return jdbcTemplate.queryForList(sql, params.toArray());
}

    /**
     * Retrieves all photocards with their type, including additional information about the source and artist.
     *
     * @return a list of photocards with their type.
     */
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

    /**
     * Retrieves a list of all photocards' names for IDs less than 15.
     *
     * @return a list of photocard names.
     */
    public List<String> getAllPcs() {
        String query = "SELECT pc_name FROM photocards WHERE pc_id < 15";
        try {
            return jdbcTemplate.queryForList(query, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la return jdbcTemplate.queryForList(query, Group.class);quête : " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves the name of the group associated with a given artist ID.
     *
     * @param artistId the ID of the artist.
     * @return the name of the group associated with the artist, or {@code null} if no group is found.
     */
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

    /**
     * Retrieves all photocards associated with a specific group ID.
     *
     * @param groupId the group ID to filter by.
     * @return a list of photocards associated with the specified group.
     */
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
            return List.of();
        }
    }
}
