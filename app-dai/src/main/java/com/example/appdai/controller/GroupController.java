package com.example.appdai.controller;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Photocard;
import com.example.appdai.service.GroupService;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.stream.Collectors;

/*
Le package controller regroupe toutes les classes responsables de gérer les requêtes HTTP (les endpoints).
Chaque controller correspond généralement à une entité ou une ressource spécifique.

Exemple :
    GroupController : Gère les routes liées aux groupes.
 */
@Component
public class GroupController {

    private final Javalin app;
    private final GroupService groupService;

    public GroupController(Javalin app, GroupService service) {
        this.app = app;
        this.groupService = service;
    }
    public void registerRoutes(Javalin app) {

        // Get the list of all groups names for the dropdown menu
        app.get("/api/groupslist", ctx -> {
            List<Group> groups = groupService.getAllGroupNames();
            if (!groups.isEmpty()) {
                ctx.status(200).json(groups);
            } else {
                ctx.status(404).result("No groups found");
            }
        });

        // Get the list of artists for a specific group
        app.get("/api/groups/{groupId}/artists", ctx -> {
            int groupId = Integer.parseInt(ctx.pathParam("groupId"));
            List<Map<String, Object>> artists = groupService.getArtistsByGroupId(groupId);
            if (!artists.isEmpty()) {
                ctx.status(200).json(artists);
            } else {
                ctx.status(404).result("No artists found for this group");
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