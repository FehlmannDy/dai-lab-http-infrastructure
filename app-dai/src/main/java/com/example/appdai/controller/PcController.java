package com.example.appdai.controller;

import io.javalin.Javalin;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Pc;
import com.example.appdai.service.PcService;
import org.springframework.stereotype.Controller;

import javax.smartcardio.Card;
import java.util.List;

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

    public PcController(Javalin app) {
        this.app = app;
        this.cardService = new PcService();
    }

    // Méthode pour enregistrer les routes
    public void registerRoutes(Javalin app) {
        app.get("/api/coucoutoi", ctx -> ctx.result("Coucou toi!"));
        app.get("/api/hello", ctx -> ctx.result("Hello depuis PcController!"));
    }

    // get hello from javalin
    @PostConstruct
    public void startJavalin() {
        app.get("/api/coucoutoi", ctx -> ctx.result("COUCOUUUUUUUUUUUUUUUUUUU"));
    }

    @PostConstruct
    public void configureRoutes() {
        // Endpoint pour récupérer toutes les cartes
        app.get("/api/cards", ctx -> {
            List<Pc> cards = cardService.getAllCards();
            ctx.json(cards);
        });

        // Endpoint pour créer une nouvelle carte
        app.post("/api/cards", ctx -> {
            Pc card = ctx.bodyAsClass(Pc.class);
            cardService.createCard(card);
            ctx.status(201).json("Card created successfully!");
        });
    }
}




