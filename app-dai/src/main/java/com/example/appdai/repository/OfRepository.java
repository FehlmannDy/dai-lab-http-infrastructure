package com.example.appdai.repository;

import com.example.appdai.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

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

    // proposeOfficialSource
    public void proposeOfficialSource(String title, String versionName, String releaseDate, PC_type type) {
        String query = "INSERT INTO official_sources (title, version_name, release_date, type, proposed) " +
                "VALUES (?, ?, ?, ?, TRUE)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setString(2, releaseDate);
                stmt.setString(3, versionName);
                stmt.setString(4, type.toString());
                return stmt;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while proposing official source: " + e.getMessage());
        }
    }


//
//    public List<OfficialSource> getAllSources(){
//        String query = "SELECT * FROM official_sources WHERE proposed = false";
//
//        try{
//            return jdbcTemplate.query(query,(rs, rowNum)->{
//                OffcialSource officialsource = new OffcialSource();
//                officialsource.setOfficial_sources_id(rs.getInt("officialSource_id"));
//                officialsource.setTitle(rs.getString("title"));
//                officialsource.setVersion_name(rs.getString("versionName"));
//                officialsource.setShopName(rs.getString("shopName"));
//                officialsource.setPc_type(rs.getPc_type("type"));
//            });
//
//        }catch (Exception e){
//            System.err.println("Erreur lors de la requête : "+e.getMessage());
//            return List.of();
//        }
//    }

    public boolean addSource(OfficialSource officialSource){
        String query = "INSERT INTO official_sources (title,version_name,release_date,type) VALUES (?,?,?,?)";

        try{
            int insertRows = jdbcTemplate.update(query,officialSource.getTitle(),officialSource.getVersion_name(), officialSource.getType());
            return insertRows > 0;

        }catch(DataAccessException e){
            System.err.println("Erreur lors de l'ajout de l'official source : " + e.getMessage());
            return false;
        }
    }

    public boolean validSource(int officialSourceId, boolean proposed){
        String query = "UPDATE official_sources SET proposed = ? WHERE id = ?";

        try{
            int updateRows = jdbcTemplate.update(query,proposed,officialSourceId);
            return updateRows > 0;
        }catch (DataAccessException e){
            System.err.println("Erreur de la validité de l'official source : " + e.getMessage());
            return false;
        }
    }
}