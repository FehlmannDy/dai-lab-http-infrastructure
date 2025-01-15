package com.example.appdai.controller;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
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

    CardController : Gère les routes liées aux cartes.
    UserController : Gère les routes liées aux utilisateurs.
 */
@Component
public class PcController {

    private final Javalin app;
    private final PcService cardService;

    public PcController(Javalin app, PcService service) {
        this.app = app;
        this.cardService = service;
    }

    // Méthode pour enregistrer les routes
    // Ce serait cool de séparer les routes get, post, etc, etc ou meme
    // davoir une liste de route ou un délire du genre
    public void registerRoutes(Javalin app) {
        app.get("/api/coucoutoi", ctx -> {
            String pcs = cardService.getFirstRecord();
            ctx.status(200).json(Map.of("pc_name", pcs));
        });
        app.get("/api/hello", ctx -> ctx.result("Hello depuis PcController!"));
        app.get("/api/allcards", ctx -> {
            List<String> allPcs = cardService.getAllPcs();
            ctx.status(200).json(Map.of("cards", allPcs));
        });

        app.get("/api/allcardswithtype", ctx -> {
            List<Photocard> allPhotocards = cardService.getAllPcsWithType();
            ctx.json(allPhotocards); // Automatiquement converti en JSON
        });

        app.get("/api/artist/{artistId}/group", ctx -> {
            int artistId = Integer.parseInt(ctx.pathParam("artistId")); // Récupère l'ID de l'artiste depuis l'URL
            String groupName = cardService.getArtistGroup(artistId);

            if (groupName != null) {
                ctx.status(200).json(Map.of("group_name", groupName));
            } else {
                ctx.status(404).result("Groupe non trouvé pour l'artiste");
            }
        });

        // Route pour obtenir la liste de tous les groupes (où proposed = false)
        app.get("/api/groups", ctx -> {
            List<Group> allGroups = cardService.getAllGroups();
            ctx.status(200).json(allGroups); // Convertir la liste de groupes en JSON
        });

        // Route pour obtenir les photocards par ID de groupe
        app.get("/api/groups/{groupId}/photocards", ctx -> {
            int groupId = Integer.parseInt(ctx.pathParam("groupId")); // Récupère l'ID du groupe depuis l'URL
            List<Photocard> photocards = cardService.getPhotocardsByGroup(groupId);

            if (photocards != null && !photocards.isEmpty()) {
                ctx.status(200).json(photocards); // Convertir la liste de photocards en JSON
            } else {
                ctx.status(404).result("Aucune photocard trouvée pour ce groupe");
            }
        });

        app.get("/api/artists/{groupsName}",ctx->{
            String groupsName = ctx.pathParam("groupsName");
            List<Artist> artists = cardService.getGroupArtists(groupsName);
            if (artists != null && !artists.isEmpty()) {
                ctx.status(200).json(artists);
            } else {
                ctx.status(404).result("Aucune photocard trouvée pour ce groupe");
            }
        });

    }
}





