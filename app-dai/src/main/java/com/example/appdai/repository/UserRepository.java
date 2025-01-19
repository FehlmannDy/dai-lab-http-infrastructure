package com.example.appdai.repository;

import com.example.appdai.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collections;
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
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Add or update a photocard in the user's list (wishlist or collection)
     * @param userId user ID
     * @param photocardId photocard ID
     * @param have true if the user has the photocard, false if it's in the wishlist
     */
    public void addOrUpdatePhotocard(int userId, int photocardId, boolean have) {
        String query = "INSERT INTO users_photocard_list (user_id, pc_id, have) VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, userId);
                stmt.setInt(2, photocardId);
                stmt.setBoolean(3, have);
                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while adding/updating photocard to user list: " + e.getMessage());
        }
    }

    /**
     * Delete a photocard from the user's list
     * @param userId user ID
     * @param photocardId photocard ID
     */
    public void deletePcFromUserList(int userId, int photocardId) {
        String query = "DELETE FROM users_photocard_list WHERE user_id = ? AND pc_id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(conn -> {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                stmt.setInt(2, photocardId);
                return stmt;
            });

            if (rowsAffected == 0) {
                throw new RuntimeException("No photocard found or deleted for this user");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while deleting photocard from user list: " + e.getMessage());
        }
    }

    /**
     * Get the wishlist of a user
     * @param userId user ID
     * @return a list of maps containing the photocard ID and name (Will be changed to model)
     */
    public List<Map<String, Object>> getUserWishlist(int userId) {
        String query = "SELECT p.pc_id, p.pc_name " +
                "FROM users_photocard_list upl " +
                "JOIN photocards p ON upl.pc_id = p.pc_id " +
                "WHERE upl.user_id = ? AND upl.have = FALSE";

        try {
            return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
                Map<String, Object> wishlistItem = new HashMap<>();
                wishlistItem.put("pc_id", rs.getInt("pc_id"));
                wishlistItem.put("pc_name", rs.getString("pc_name"));
                return wishlistItem;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de la récupération de la wishlist : " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Get the collection of a user
     * @param userId user ID
     * @return a list of maps containing the photocard ID and name (Will be changed to model)
     */
    public List<Map<String, Object>> getUserCollection(int userId) {
        String query = "SELECT p.pc_id, p.pc_name " +
                "FROM users_photocard_list upl " +
                "JOIN photocards p ON upl.pc_id = p.pc_id " +
                "WHERE upl.user_id = ? AND upl.have = TRUE";

        try {
            return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
                Map<String, Object> wishlistItem = new HashMap<>();
                wishlistItem.put("pc_id", rs.getInt("pc_id"));
                wishlistItem.put("pc_name", rs.getString("pc_name"));
                return wishlistItem;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de la récupération de la wishlist : " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Propose a photocard
     * @param name name of the photocard
     * @param shopName shop name of the photocard (optional)
     * @param imageUrl image URL of the photocard
     * @param pcType type of the photocard
     * @param artistId ID of the artist
     * @param sourceId ID of the official source
     */
    public void proposePhotocard(String name, String shopName, String imageUrl, String pcType, Integer artistId,
                                     Integer sourceId) {
        if (name == null || name.isEmpty() || pcType == null || imageUrl == null || artistId == null ||
                sourceId == null) {
            throw new IllegalArgumentException("Every field is required but shop name is optional");
        }

        String query = "INSERT INTO photocards (pc_name, shop_name, url, pc_type, proposed, artists_id, official_sources_id) " +
                "VALUES (?, ?, ?, CAST(? AS pc_type_enum), TRUE, ?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, name);

                if ("POB".equalsIgnoreCase(pcType) && shopName != null && !shopName.isEmpty()) {
                    stmt.setString(2, shopName);
                } else {
                    stmt.setNull(2, Types.VARCHAR);
                }

                stmt.setString(3, imageUrl);
                stmt.setString(4, pcType);
                stmt.setInt(5, artistId);
                stmt.setInt(6, sourceId);

                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while adding proposed photocard: " + e.getMessage(), e);
        }
    }


    // ---------------------------------------------------------------------------------------------
    // ADMIN METHODS

    //bon on peut refactor les deux hein
    /**
     * Accept proposed photocards
     * @param photocardIds list of photocard IDs
     */
    public void acceptProposedPhotocard(List<Integer> photocardIds) {
        if (photocardIds == null || photocardIds.isEmpty()) {
            throw new IllegalArgumentException("Photocard IDs list cannot be null or empty");
        }

        String query = "UPDATE photocards SET proposed = FALSE WHERE pc_id = ANY(?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                Array sqlArray = connection.createArrayOf("INTEGER", photocardIds.toArray());
                stmt.setArray(1, sqlArray);
                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while accepting proposed photocards: " + e.getMessage());
        }
    }

    /**
     * Reject proposed photocards
     * @param photocardIds list of photocard IDs
     */
    public void rejectProposedPhotocard(List<Integer> photocardIds) {
        if (photocardIds == null || photocardIds.isEmpty()) {
            throw new IllegalArgumentException("Photocard IDs list cannot be null or empty");
        }

        String query = "DELETE FROM photocards WHERE pc_id = ANY(?) AND proposed = TRUE";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                Array sqlArray = connection.createArrayOf("INTEGER", photocardIds.toArray());
                stmt.setArray(1, sqlArray);
                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while rejecting proposed photocards: " + e.getMessage());
        }
    }
}