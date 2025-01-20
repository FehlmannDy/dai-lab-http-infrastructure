package com.example.appdai.controller;

import com.example.appdai.model.*;

import com.example.appdai.service.GroupService;
import com.example.appdai.service.OfService;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Handles HTTP requests related to OfficialSource resources.
 *
 * <p>This controller defines routes for interacting with official sources in the application. The following operations are supported:</p>
 * <ul>
 *   <li>Proposing a new official source.</li>
 * </ul>
 *
 * <p>The routes are registered using the provided Javalin application instance and interact with the {@link OfService} layer for business logic.</p>
 */
@Component
public class OfController {

    private final Javalin app;
    private final OfService ofService;

    /**
     * Constructs a new {@code OfController} with the specified Javalin app and {@link OfService}.
     *
     * @param app the Javalin application instance used to register routes
     * @param service the service layer responsible for group-related operations
     */
    public OfController(Javalin app, OfService service) {
        this.app = app;
        this.ofService = service;
    }

    /**
     * Registers the routes for official source related endpoints.
     *
     * <p>The following endpoint is defined:</p>
     * <ul>
     *   <li><b>POST /api/officialsource/propose</b>: Proposes a new official source by accepting relevant data and passing it to the service layer.</li>
     * </ul>
     *
     * @param app the Javalin application instance where the routes are registered
     */
    public void registerRoutes(Javalin app) {

        // Proposes a new official source by accepting relevant data and passing it to the service layer.
        app.post("/api/officialsource/propose", ctx -> {

            String groupName = ctx.queryParam("groupName");
            String title = ctx.queryParam("title");
            String type = ctx.queryParam("type");
            String versionName = ctx.queryParam("version_name");

            if (groupName == null || groupName.isEmpty() || title == null || title.isEmpty() || type == null || type.isEmpty()) {
                ctx.status(400).result("Missing required parameters");
                return;
            }

            OfficialSource officialSource = new OfficialSource();
            officialSource.setTitle(title);
            officialSource.setVersion_name(versionName);

            try {
                if (type != null && !type.isEmpty()) {
                    OF_type ofType = OF_type.valueOf(type.toUpperCase());
                    officialSource.setType(ofType);
                }
            } catch (IllegalArgumentException e) {
                ctx.status(400).result("Invalid type. Allowed values are: ALBUM, OTHER, EVENT");
                return;
            }

            try {
                ofService.proposeOfficialSource(officialSource);
                ctx.status(201).result("Official source proposed successfully");
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error");
            }
        });


        // Fetches official sources for a specific group by group name
        app.get("/api/groups/official-sources", ctx -> {
            try {
                String groupName = ctx.queryParam("groupName");

                List<Map<String, Object>> officialSources = ofService.getOfficialSourcesForGroup(groupName);

                if (officialSources != null && !officialSources.isEmpty()) {
                    ctx.status(200).json(officialSources);
                } else {
                    ctx.status(404).result("No official sources found for the specified group");
                }
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error occurred");
            }
        });
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
    }
}