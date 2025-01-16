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

import java.util.List;

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

    public List<Photocard> getUserWishlist(Integer userId) {
        String query = "SELECT pc.pc_id,pc.pc_name,pc.url,pc.pc_type,pc.proposed,pc.artists_id,pc.official_sources_id\n" +
                "FROM users_photocard_list AS upl\n" +
                "JOIN photocards AS pc USING (pc_id)\n"+
                "WHERE upl.users_id = ? AND upl.have = false";

        try {
            return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
                Photocard photocard = new Photocard();
                photocard.setId(rs.getInt("pc_id"));
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
            e.printStackTrace(); // Pour plus de détails sur l'erreur
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public boolean updateWishlist(int userId, int photocardId, boolean have) {
        String query = "UPDATE user_photocard_list SET have = ? WHERE user_id = ? AND photocard_id = ?";
    
        try {
            int updatedRows = jdbcTemplate.update(query, have, userId, photocardId);
            return updatedRows > 0;
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de la mise à jour de la wishlist : " + e.getMessage());
            return false;
        }
    }

    public boolean addToWishlist(int userId, int photocardId, boolean have) {
        String query = "INSERT INTO user_photocard_list (user_id, photocard_id, have) VALUES (?, ?, ?)";

        try {
            int insertedRows = jdbcTemplate.update(query, userId, photocardId,have);
            return insertedRows > 0; // Retourne true si au moins une ligne a été insérée
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'ajout de la photocard à la wishlist : " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFromWishlist(int userId, int photocardId) {
        String query = "DELETE FROM user_photocard_list WHERE user_id = ? AND photocard_id = ?";
    
        try {
            int deletedRows = jdbcTemplate.update(query, userId, photocardId);
            return deletedRows > 0; // Retourne true si au moins une ligne a été supprimée
        } catch (DataAccessException e) {
            System.err.println("Erreur lors de la suppression de la photocard de la wishlist : " + e.getMessage());
            return false;
        }
    }
}