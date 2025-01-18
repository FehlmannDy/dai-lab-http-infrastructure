package com.example.appdai.repository;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.OfficialSource;
import com.example.appdai.model.Photocard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
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