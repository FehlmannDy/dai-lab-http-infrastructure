//package com.example.appdai.controller;
//
//import com.example.appdai.model.Artist;
//import com.example.appdai.model.Group;
//import com.example.appdai.model.OfficialSource;
//
//import io.javalin.Javalin;
//import org.springframework.stereotype.Component;
//import com.example.appdai.model.Photocard;
//import com.example.appdai.service.PcService;
//
//import java.util.List;
//import java.util.Map;
//import java.time.LocalDate;
//
///*
//Le package controller regroupe toutes les classes responsables de gérer les requêtes HTTP (les endpoints).
//Chaque controller correspond généralement à une entité ou une ressource spécifique.
//
//Exemple :
//    OfController : Gère les routes liées aux OffcialSources.
// */
//@Component
//public class OfController {
//
//    private final Javalin app;
//    private final OfService officialSourceService;
//
//    public OfController(Javalin app, OfService service) {
//        this.app = app;
//        this.officialSourceService = service;
//    }
//    public void registerRoutes(Javalin app) {
//
//        // GET: la liste de tous les groupes (où proposed = false)
//        app.get("/api/officialsources", ctx -> {
//            List<OfficialSource> allSources = officialSourceService.getAllSources();
//            if(allSources != null && !allSources.isEmpty()){
//                ctx.status(200).json(allSources);
//            }else{
//                ctx.status(404).result("Error no official sources handling");
//            }
//        });
//
//        // INSERT: ajout une Official source
//        app.patch("/api/add/officielsource",ctx ->{
//            OfficialSource officialsource = ctx.bodyAsClass(OfficialSource.class);
//            if (officialSourceService.addSource(officialsource)) {
//                ctx.status(201).result("Official source added successfully");
//            } else {
//                ctx.status(400).result("Error adding Official source");
//            }
//        });
//
//        // UPDATE: official source validée par les admins
//       app.patch("/api/admin/officialsource",ctx->{
//            Int officialSourceId = Int.parseInt(ctx.body("officialSourceId"));
//            if(officialSourceId != null){
//                if(officialSourceService.validSource(officialSourceId)){
//                    ctx.status(201).result("Official source valid successfully");
//                }else{
//                    ctx.status(400).result("Error valid Official source");
//                }
//            }else{
//                ctx.status(404).result("Error Official source id not exist");
//            }
//       });
//    }
//}