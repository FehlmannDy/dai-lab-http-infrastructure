package com.example.appdai.repository;

import com.example.appdai.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * Repository class to interact with the database for user-related operations, specifically
 * managing the user's photocard list (wishlist and collection).
 *
 * This class provides methods for adding, updating, deleting, retrieving, and proposing photocards
 * in the user's list, as well as handling administrative actions related to proposed photocards.
 */
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a {@link UserRepository} with the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate The {@link JdbcTemplate} used for database operations.
     */
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Adds or updates a photocard in the user's list (either wishlist or collection).
     *
     * @param userId The ID of the user.
     * @param photocardId The ID of the photocard.
     * @param have A boolean indicating if the photocard is in the user's collection (true) or wishlist (false).
     * @throws RuntimeException If an error occurs while adding or updating the photocard.
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
     * Deletes a photocard from the user's list.
     *
     * @param userId The ID of the user.
     * @param photocardId The ID of the photocard to be deleted.
     * @throws RuntimeException If no photocard is found or deleted, or if an error occurs.
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
     * Retrieves the collection of photocards for a specific user, filtered by their "have" status (owned or not).
     *
     * @param userId The ID of the user.
     * @param have A boolean that specifies if the retrieved photocards are owned (true) or not (false).
     * @return A list of {@link Photocard} representing the user's collection.
     *         Returns an empty list if no matching photocards are found or if an error occurs.
     * @throws DataAccessException If an error occurs while retrieving the collection from the database.
     */
    public List<Photocard> getUserCollection(int userId, boolean have) {
        String query = "SELECT p.pc_id, p.pc_name, p.shop_name, p.url, p.pc_type, p.artists_id, p.official_sources_id, a.stage_name, o.title " +
                "FROM users_photocard_list upl " +
                "JOIN photocards p ON upl.pc_id = p.pc_id " +
                "JOIN artists a ON p.artists_id = a.artists_id " +
                "JOIN official_sources o ON p.official_sources_id = o.official_sources_id " +
                "WHERE upl.user_id = ? AND upl.have = ?";

        try {
            return jdbcTemplate.query(query, new Object[]{userId,have}, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setPc_id(rs.getInt("pc_id"));
                photocard.setPc_name(rs.getString("pc_name"));
                photocard.setArtists_id(rs.getInt("artists_id"));
                photocard.setOfficial_sources_id(rs.getInt("official_sources_id"));
                photocard.setUrl(rs.getString("url"));
                photocard.setShop_name(rs.getString("shop_name"));
                photocard.setProposed(false);
                return photocard;
            });
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de la récupération de la wishlist : " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Proposes a photocard for submission.
     *
     * @param photocard The {@link Photocard} to be proposed.
     * @throws IllegalArgumentException If any required field of the photocard is null or empty.
     * @throws RuntimeException If an error occurs while adding the proposed photocard.
     */
    public void proposePhotocard(Photocard photocard) {
        if (photocard.getPc_name() == null || photocard.getPc_name().isEmpty() || photocard.getPc_type() == null || photocard.getUrl() == null) {
            throw new IllegalArgumentException("Every field is required but shop name is optional");
        }
        String query = "INSERT INTO photocards (pc_name, shop_name, url, pc_type, proposed, artists_id, official_sources_id) " +
                "VALUES (?, ?, ?, CAST(? AS pc_type_enum), TRUE, ?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, photocard.getPc_name());

                if (photocard.getPc_type() == PC_type.POB && photocard.getShop_name() != null && !photocard.getShop_name().isEmpty()) {
                    stmt.setString(2, photocard.getShop_name());
                } else {
                    stmt.setNull(2, Types.VARCHAR);
                }

                stmt.setString(3, photocard.getUrl());
                stmt.setString(4, photocard.getPc_type().toString());
                stmt.setInt(5, photocard.getArtists_id());
                stmt.setInt(6, photocard.getOfficial_sources_id());

                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while adding proposed photocard: " + e.getMessage(), e);
        }
    }

    public User checkLogin(User user) {
        if(user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Every field is required but shop name is optional");
        }
        String query = "SELECT * FROM users_photocard_list WHERE username = ? AND password = ?";
        try{
            jdbcTemplate.query(query,new Object[]{user.getUsername(),user.getPassword()},(rs, row)->{
                User user1 = new User();
                user1.setUser_id(rs.getInt("user_id"));
                user1.setUsername(rs.getString("username"));
                user1.setIs_admin(rs.getBoolean("is_admin"));
                user1.setPassword(null);
                return user1;
            });
        }catch (DataAccessException e) {
            throw new RuntimeException("Error while checking username : " + e.getMessage());
        }
        return null;
    }


    // --------------- ADMIN METHODS ---------------

    //TODO bon on peut refactor les deux hein
    /**
     * Accepts proposed photocards by updating their status.
     *
     * @param photocardIds A list of photocard IDs to accept.
     * @throws IllegalArgumentException If the list of photocard IDs is null or empty.
     * @throws RuntimeException If an error occurs while updating the proposed photocards.
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
     * Rejects proposed photocards by deleting them from the database.
     *
     * @param photocardIds A list of photocard IDs to reject.
     * @throws IllegalArgumentException If the list of photocard IDs is null or empty.
     * @throws RuntimeException If an error occurs while rejecting the proposed photocards.
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