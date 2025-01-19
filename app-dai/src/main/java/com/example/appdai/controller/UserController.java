package com.example.appdai.controller;

import com.example.appdai.service.UserService;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Photocard;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller class responsible for handling HTTP requests related to users and their photocards.
 *
 * This class defines various routes for managing users' wishlist, collection, and proposed photocards.
 * It provides endpoints to add, update, delete, and retrieve photocards from a user's wishlist and collection.
 * It also supports the functionality for proposing, accepting, and rejecting photocards.
 *
 **/
@Component
public class UserController {

    private final Javalin app;
    private final UserService userService;

    /**
     * Constructs a new {@code UserController} with the specified Javalin app and {@link UserController}.
     *
     * @param app the Javalin application instance used to register routes
     * @param service the service layer responsible for group-related operations
     */
    public UserController(Javalin app, UserService service) {
        this.app = app;
        this.userService = service;
    }

    /**
     * Registers all the routes for the UserController.
     * <p>
     * This method defines the various endpoints for handling user and photocard related requests.
     * </p>
     *
     * <ul>
     *   <li>POST /api/users/{userId}/photocards: Adds or updates a photocard in the user's wishlist or collection.</li>
     *   <li>DELETE /api/users/{userId}/photocards/{photocardId}: Deletes a photocard from the user's wishlist or collection.</li>
     *   <li>GET /api/users/{userId}/wishlist: Retrieves the user's wishlist.</li>
     *   <li>GET /api/users/{userId}/collection: Retrieves the user's collection.</li>
     *   <li>POST /api/photocards/proposecard: Proposes a new photocard.</li>
     *   <li>PATCH /api/admin/accept: Accepts proposed photocards.</li>
     *   <li>PATCH /api/admin/reject: Rejects proposed photocards.</li>
     * </ul>
     *
     * @param app the Javalin app instance used to register the routes
     */
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
            List<Photocard> photocards = userService.getUserWishlist(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user wishlist");
            }
        });

        // Get the collection of a user by userId
        app.get("/api/users/{userId}/collection", ctx -> {
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            List<Photocard> photocards = userService.getUserCollection(userId);
            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards);
            }else{
                ctx.status(404).result("No photocards in user collection");
            }
        });

        app.post("/api/photocards/proposecard", ctx -> {
            Photocard body = ctx.bodyAsClass(Photocard.class);
            userService.proposePhotocard(body);
            ctx.status(201).result("Photocard proposed successfully");
        });

        //--------------- ADMIN Methods ---------------

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