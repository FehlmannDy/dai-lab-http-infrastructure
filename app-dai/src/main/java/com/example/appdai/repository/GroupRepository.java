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
public class GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

}