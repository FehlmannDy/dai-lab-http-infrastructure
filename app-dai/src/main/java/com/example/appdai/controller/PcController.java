package com.example.appdai.controller;

import io.javalin.Javalin;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Pc;
import com.example.appdai.service.PcService;
import org.springframework.stereotype.Controller;

import javax.smartcardio.Card;
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
            List<Pc> allPcs = cardService.getAllPcsWithType();
            ctx.json(allPcs); // Automatiquement converti en JSON
        });

    }
}





