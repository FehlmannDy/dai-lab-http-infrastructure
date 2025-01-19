package com.example.appdai.controller;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.PC_type;
import com.example.appdai.service.UserService;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Photocard;
import com.example.appdai.service.PcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
Le package controller regroupe toutes les classes responsables de gérer les requêtes HTTP (les endpoints).
Chaque controller correspond généralement à une entité ou une ressource spécifique.

Exemple :
    UserController : Gère les routes liées aux utilisateurs.
 */
@Component
public class UserController {

    private final Javalin app;
    private final UserService userService;


    public UserController(Javalin app, UserService service) {
        this.app = app;
        this.userService = service;
    }

    public void registerRoutes(Javalin app) {

        // Add or update a photocard in the wishlist/collection
        app.post("/api/users/{userId}/photocards", ctx -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            int photocardId = Integer.parseInt(Objects.requireNonNull(ctx.formParam("photocardId")));
            String haveStr = ctx.formParam("have");

            if (!"true".equalsIgnoreCase(haveStr) && !"false".equalsIgnoreCase(haveStr)) {
                ctx.status(400).result("Invalid value for 'have'. Must be 'true' or 'false'.");
                return;
            }

            boolean have = Boolean.parseBoolean(haveStr);

            try {
                userService.addOrUpdatePhotocard(userId, photocardId, have);
                ctx.status(201);
            } catch (RuntimeException e) {
                ctx.status(400).result(e.getMessage());
            }
        });

        // Delete a photocard from the wishlist/collection
        app.delete("/api/users/{userId}/photocards/{photocardId}", ctx -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            int photocardId = Integer.parseInt(ctx.pathParam("photocardId"));
            userService.removeFromUserlist(userId, photocardId);
            ctx.status(204).result("Photocard removed from user list");
        });



        // Get the wishlist of a user by userId
        app.get("/api/users/{userId}/wishlist", ctx -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            List<Map<String, Object>> photocards = userService.getUserWishlist(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user wishlist");
            }
        });

        // Get the collection of a user by userId
        app.get("/api/users/{userId}/collection", ctx -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            List<Map<String, Object>> photocards = userService.getUserCollection(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user collection");
            }
        });

        app.post("/api/photocards/proposecard", ctx -> {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);

            String pcName = (String) body.get("pcName");
            String pcType = (String) body.get("pcType");
            String imageUrl = (String) body.get("imageUrl");
            Integer artistId = (Integer) body.get("artistId");
            Integer sourceId = (Integer) body.get("sourceId");
            String shopName = (String) body.get("shopName");

            userService.proposePhotocard(pcName, shopName, imageUrl, pcType, artistId, sourceId);
            ctx.status(201).result("Photocard proposed successfully");
        });


        // Accept proposed photocards
        app.patch("/api/admin/accept", ctx -> {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            List<Integer> photocardIds = (List<Integer>) body.get("photocardIds");

            if (photocardIds == null || photocardIds.isEmpty()) {
                ctx.status(400).result("Photocard IDs list cannot be null or empty");
                return;
            }

            userService.acceptProposedPhotocard(photocardIds);
            ctx.status(200).result("Proposed photocards accepted");
        });

        // Reject proposed photocards
        app.patch("/api/admin/reject", ctx -> {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            List<Integer> photocardIds = (List<Integer>) body.get("photocardIds");

            if (photocardIds == null || photocardIds.isEmpty()) {
                ctx.status(400).result("Photocard IDs list cannot be null or empty");
                return;
            }

            userService.rejectProposedPhotocard(photocardIds);
            ctx.status(200).result("Proposed photocards rejected");
        });






//
//        // INSERT: insert un élément de la liste de souhaits
//        app.patch("/api/users/{userId}/add/wishlist/{photocardId}", ctx -> {
//            Integer userId = Integer.parseInt(ctx.pathParam("userId"));
//            Integer photocardId = Integer.parseInt(ctx.pathParam("photocardId"));
//            Boolean have = Boolean.parseBoolean(ctx.pathParam("have"));
//            if (userId != null || photocardId != null) {
//                userService.removeFromWishlist(userId, photocardId,have);
//                ctx.status(204).result("Photocard ajoutée à la wishlist");
//            }else{
//                ctx.status(404).result("Le user n'existe pas ou la carte n'existe pas");
//            }
//
//        });
//
//        // PATCH: Modifier un élément de la liste de souhaits (having)
//        app.patch("/api/users/{userId}/update/wishlist/{photocardId}", ctx -> {
//            Integer userId = Integer.parseInt(ctx.pathParam("userId"));
//            Integer photocardId = Integer.parseInt(ctx.pathParam("photocardId"));
//            Photocard updatedPhotocard = ctx.bodyAsClass(Photocard.class);
//            if (userId != null || photocardId != null) {
//                userService.updateWishlist(userId, photocardId, updatedPhotocard);
//                ctx.status(200).result("Photocard mise à jour");
//            }else{
//                ctx.status(404).result("Le user n'existe pas ou la carte n'existe pas");
//            }
//        });
//

    }

}