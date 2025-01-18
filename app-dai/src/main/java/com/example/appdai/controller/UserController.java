package com.example.appdai.controller;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.service.UserService;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Photocard;
import com.example.appdai.service.PcService;

import java.util.List;
import java.util.Map;

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

        // Get the wishlist of a user by userId
        app.get("/api/users/{userId}/wishlist", ctx -> {
            Integer userId = Integer.parseInt(ctx.pathParam("userId"));
            List<Map<String, Object>> photocards = userService.getUserWishlist(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user wishlist");
            }
        });

        // Get the collection of a user by userId
        app.get("/api/users/{userId}/collection", ctx -> {
            Integer userId = Integer.parseInt(ctx.pathParam("userId"));
            List<Map<String, Object>> photocards = userService.getUserCollection(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user collection");
            }
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
//        // DELETE: Supprimer un élément de la liste de souhaits
//        app.delete("/api/users/{userId}/wishlist/{photocardId}", ctx -> {
//            Integer userId = Integer.parseInt(ctx.pathParam("userId"));
//            Integer photocardId = Integer.parseInt(ctx.pathParam("photocardId"));
//            if (userId != null || photocardId != null) {
//                userService.removeFromWishlist(userId, photocardId);
//                ctx.status(204).result("Photocard supprimée de la wishlist");
//            }else{
//                ctx.status(404).result("Le user n'existe pas ou la carte n'existe pas");
//            }
//        });
    }

}