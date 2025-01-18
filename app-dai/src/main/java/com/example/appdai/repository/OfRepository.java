package com.example.appdai.repository;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.OfficialSource;
import com.example.appdai.model.OfficialSource.*;
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
public class OfRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OfRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//
//    public List<OfficialSource> getAllSources(){
//        String query = "SELECT * FROM official_sources WHERE proposed = false";
//
//        try{
//            return jdbcTemplate.query(query,(rs, rowNum)->{
//                OffcialSource officialsource = new OffcialSource();
//                officialsource.setId(rs.getInt("officialSource_id"));
//                officialsource.setTitle(rs.getString("title"));
//                officialsource.setVersionName(rs.getString("versionName"));
//                officialsource.setShopName(rs.getString("shopName"));
//                officialsource.setType(rs.getType("type"));
//            });
//
//        }catch (Exception e){
//            System.err.println("Erreur lors de la requête : "+e.getMessage());
//            return List.of();
//        }
//    }

    public boolean addSource(OfficialSource officialSource){
        String query = "INSERT INTO official_sources (title,version_name,shop_name,release_date,type) VALUES (?,?,?,?,?)";

        try{
            int insertRows = jdbcTemplate.update(query,officialSource.getTitle(),officialSource.getVersionName(),officialSource.getShopName(),officialSource.getType());
            return insertRows > 0;

        }catch(DataAccessException e){
            System.err.println("Erreur lors de l'ajout de l'official source : " + e.getMessage());
            return false;
        }
    }

    public boolean validSource(int officialSourceId){
        String query = "UPDATE official_sources SET proposed = ? WHERE id = ?";

        try{
            int updateRows = jdbcTemplate.update(query,officialSourceId);
            return updateRows > 0;
        }catch (DataAccessException e){
            System.err.println("Erreur de la validité de l'official source : " + e.getMessage());
            return false;
        }
    }
}