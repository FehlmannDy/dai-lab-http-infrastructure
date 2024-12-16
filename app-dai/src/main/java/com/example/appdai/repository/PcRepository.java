package com.example.appdai.repository;

import com.example.appdai.model.Pc;
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

    public PcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pc> findAll() {
        String sql = "SELECT * FROM cards";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pc card = new Pc();
            card.setId(rs.getLong("id"));
            card.setName(rs.getString("name"));
            card.setDescription(rs.getString("description"));
            card.setOwnerUsername(rs.getString("owner_username"));
            return card;
        });
    }

    public void save(Pc card) {
        String sql = "INSERT INTO cards (name, description, owner_username) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, card.getName(), card.getDescription(), card.getOwnerUsername());
    }
}
