package com.example.appdai.repository;

import com.example.appdai.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository class for accessing data related to official sources in the database.
 * This class interacts directly with the database using JDBC to perform CRUD operations for official sources.
 *
 * <p>It contains methods to propose, add, and validate official sources in the database.</p>
 *
 * The class uses {@link JdbcTemplate} to execute SQL queries and manage database connections.
 */
@Repository
public class OfRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code OfRepository} instance.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to execute SQL queries.
     */
    @Autowired
    public OfRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Proposes an official source by inserting its details into the database.
     *
     * @param officialSource is the OfficialSource
     * @throws RuntimeException if there is an error while proposing the official source.
     */
    public void proposeOfficialSource(OfficialSource officialSource) {
        String query = "INSERT INTO official_sources (title, version_name, type, proposed) " +
                "VALUES (?, ?, ?, TRUE)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, officialSource.getTitle());
                stmt.setString(2, officialSource.getVersion_name());
                stmt.setString(3, officialSource.getType().toString());
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

    /**
     * Adds a new official source to the database.
     *
     * @param officialSource the official source to be added.
     * @return {@code true} if the source was added successfully, otherwise {@code false}.
     */
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

    /**
     * Updates the "proposed" status of an official source in the database.
     *
     * @param officialSourceId the ID of the official source to be updated.
     * @param proposed         the proposed status to set.
     * @return {@code true} if the update was successful, otherwise {@code false}.
     */
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

    public List<Map<String, Object>> getOfficialSourcesForGroup(String groupName) {
        // La requête SQL avec un paramètre pour le nom du groupe
        String query = "SELECT o.official_sources_id, o.title, o.version_name " +
                "FROM official_sources o " +
                "INNER JOIN groups_official_sources go ON o.official_sources_id = go.official_sources_id " +
                "INNER JOIN biasbinder_bst.groups g ON g.groups_id = go.groups_id " +
                "WHERE g.groups_name = ? AND o.proposed = false";

        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(query, new Object[]{groupName});

            return results.stream().map(row -> {
                Map<String, Object> result = new HashMap<>();
                String titleAndVersion = row.get("title") + " " + row.get("version_name");
                result.put("official_sources_id", row.get("official_sources_id"));
                result.put("title_version", titleAndVersion);
                return result;
            }).collect(Collectors.toList());

        } catch (DataAccessException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return List.of();
        }
    }

    //TODO supprimer la source non valide
}