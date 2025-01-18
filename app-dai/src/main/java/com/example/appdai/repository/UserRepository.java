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

import java.sql.Connection;
import java.sql.PreparedStatement;
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


//
//    public boolean updateWishlist(int userId, int photocardId, boolean have) {
//        String query = "UPDATE user_photocard_list SET have = ? WHERE user_id = ? AND photocard_id = ?";
//
//        try {
//            int updatedRows = jdbcTemplate.update(query, have, userId, photocardId);
//            return updatedRows > 0;
//        } catch (DataAccessException e) {
//            System.err.println("Erreur lors de la mise à jour de la wishlist : " + e.getMessage());
//            return false;
//        }
//    }
//

}