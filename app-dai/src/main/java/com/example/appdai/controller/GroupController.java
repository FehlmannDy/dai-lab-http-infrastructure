package com.example.appdai.controller;

import com.example.appdai.model.Group;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.service.GroupService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles HTTP requests related to groups in the application.
 *
 * <p>This controller is responsible for defining endpoints that allow interaction with group-related resources.
 * The following operations are supported:</p>
 * <ul>
 *   <li>Retrieving a list of all group names.</li>
 *   <li>Retrieving a list of artists associated with a specific group.</li>
 * </ul>
 *
 * <p>The routes are registered using the provided Javalin application instance.</p>
 */
@Component
public class GroupController {

    private final Javalin app;
    private final GroupService groupService;

    /**
     * Constructs a new {@code GroupController} with the specified Javalin app and {@link GroupService}.
     *
     * @param app the Javalin application instance used to register routes
     * @param service the service layer responsible for group-related operations
     */
    public GroupController(Javalin app, GroupService service) {
        this.app = app;
        this.groupService = service;
    }

    /**
     * Registers the routes for group-related endpoints.
     *
     * <p>The following endpoints are defined:</p>
     * <ul>
     *   <li><b>GET /api/groupslist</b>: Retrieves the list of all group names for a dropdown menu.</li>
     *   <li><b>GET /api/groups/{groupId}/artists</b>: Retrieves a list of artists for the specified group ID.</li>
     * </ul>
     *
     * @param app the Javalin application instance where the routes are registered
     */
    public void registerRoutes(Javalin app) {

        // Retrieves the list of all group names for a dropdown menu.
        app.get("/api/groupslist", ctx -> {
            List<Group> groups = groupService.getAllGroupNames();
            if (!groups.isEmpty()) {
                ctx.status(200).json(groups);
            } else {
                ctx.status(404).result("No groups found");
            }
        });

        // Retrieves a list of artists for the specified group ID.
        app.get("/api/groups/{groupId}/artists", ctx -> {
            try {
                int groupId = Integer.parseInt(ctx.pathParam("groupId"));
                List<Map<String, Object>> artists = groupService.getArtistsByGroupId(groupId);
                if (!artists.isEmpty()) {
                    ctx.status(200).json(artists);
                } else {
                    ctx.status(404).result("No artists found for this specific group");
                }
            }catch (NumberFormatException e) {
                ctx.status(400).result("Invalid group ID");
            }
            catch (Exception e) {
                ctx.status(500).result("Unexpected error");
            }
        });

//        // GET: la liste de tous les groupes (où proposed = false)
//        app.get("/api/groups", ctx -> {
//            List<Group> allGroups = groupService.getAllGroups();
//            if(allGroups != null && !allGroups.isEmpty()){
//                ctx.status(200).json(allGroups);
//            }else{
//                ctx.status(404).result("Error no group handling");
//            }
//        });
//
//        // GET: tous les groupes proposés
//        app.get("/api/proposed/groups", ctx ->{
//            List<Group> proposedGroups = groupService.getProposedGroups();
//            if (proposedGroups != null && !proposedGroups.isEmpty()) {
//                ctx.status(200).json(proposedGroups);
//            }else{
//                ctx.status(404).result("Error no proposed group handling");
//            }
//        });
//
//        // INSERT: insert un groupe dand le BDD avec proposed = true
//        app.patch("/api/add/group", ctx ->{
//            Group group = ctx.bodyAsClass(Group.class);
//            if (groupService.addGroup(group)) {
//                ctx.status(201).result("Group added successfully");
//            } else {
//                ctx.status(400).result("Error adding group");
//            }
//        });
//
//        // UPDATE: ajoute le disband date à un groupe
//        app.patch("/api/disband/group", ctx->{
//            Int groupId = Int.parseInt(ctx.body("groupId"));
//            Date disbandDate = Date.parseDate(ctx.body("disbandDate"));
//            if(disbandDate.isBefore(LocalDate.now())){
//                if(groupService.disbandGroup(groupId,disbandDate)){
//                    ctx.status(201).result("Group disband successfully");
//                }else{
//                    ctx.status(400).result("Error disband group");
//                }
//            }else{
//                ctx.status(400).result("Error group can't disband in the future");
//            }
//        });
//
//        // UPDATE: groupe validé par les admins
//        app.patch("/api/admin/group",ctx->{
//            //TODO verifier le tokcen admin
//            Int groupId = Int.parseInt(ctx.body("groupId"));
//            if(groupId != null){
//                if(groupService.validGroup(groupId)){
//                    ctx.status(201).result("Group valid succesfully");
//                }else{
//                    ctx.status(400).result("Error valid group");
//                }
//            }else{
//                ctx.status(400).result("Error groupId is not valid");
//            }
//        });
    }

}