package com.example.appdai.controller;

import com.example.appdai.service.GroupService;
import io.javalin.Javalin;
import org.springframework.stereotype.Component;
import com.example.appdai.model.Photocard;
import com.example.appdai.service.PcService;

import java.util.List;
import java.util.Map;

/**
 * The `PcController` class is responsible for handling HTTP requests related to Photocards.
 * This class defines the endpoints for accessing and managing photocards, including retrieving a list of all cards,
 * paginated results, proposed photocards, and photocards by group or artist.
 *
 * It uses Javalin for routing and depends on `PcService` for business logic related to photocards.
 *
 */
@Component
public class PcController {

    private final Javalin app;
    private final PcService cardService;

    /**
     * Constructs a new {@code PcController} with the specified Javalin app and {@link PcController}.
     *
     * @param app the Javalin application instance used to register routes
     * @param service the service layer responsible for group-related operations
     */
    public PcController(Javalin app, PcService service) {
        this.app = app;
        this.cardService = service;
    }

    /**
     * Registers the routes for Photocard related endpoints.
     *
     * <p>The controller includes the following routes:
     * <ul>
     *   <li>GET /api/hello: Simple hello endpoint to verify the controller is working.</li>
     *   <li>GET /api/allcards: Fetches a list of all photocards.</li>
     *   <li>GET /api/photocards: Fetches a paginated list of photocards, optionally filtered by group ID.</li>
     *   <li>GET /api/proposedphotocards: Fetches a list of photocards proposed by users.</li>
     *   <li>GET /api/allcardswithtype: Fetches all photocards with type information.</li>
     *   <li>GET /api/artist/{artistId}/group: Fetches the group associated with a given artist.</li>
     *   <li>GET /api/groups/{groupId}/photocards: Fetches photocards for a specific group based on group ID.</li>
     * </ul>
     */
    public void registerRoutes(Javalin app) {

        // Fetches all photocards
        app.get("/api/allcards", ctx -> {
            List<String> allPcs = cardService.getAllPcs();
            if (allPcs == null || allPcs.isEmpty()) {
                ctx.status(404);
            } else {
                ctx.status(200).json(Map.of("cards", allPcs));
            }

        });

        // Fetches photocards with pagination and optional group filtering
        app.get("/api/photocards", ctx -> {
            try {

                String groupIdParam = ctx.queryParam("groupId");
                Integer groupId = (groupIdParam != null && !groupIdParam.isEmpty()) ? Integer.parseInt(groupIdParam) : null;

                String artistIdParam = ctx.queryParam("artistId");
                Integer artistId = (artistIdParam != null && !artistIdParam.isEmpty()) ? Integer.parseInt(artistIdParam) : null;

                int page = Integer.parseInt(ctx.queryParam("page") != null ? ctx.queryParam("page") : "1");
                int size = Integer.parseInt(ctx.queryParam("size") != null ? ctx.queryParam("size") : "24");

                if (page <= 0 || size <= 0) {
                    ctx.status(400).result("Page and size must be positive integers");
                    return;
                }

                List<Map<String, Object>> photocards = cardService.getPaginatedPcs(groupId, artistId, page, size);

                if (photocards == null || photocards.isEmpty()) {
                    ctx.status(404).result("No photocards found for the given parameters");
                } else {
                    ctx.status(200).json(photocards);
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid number format for 'page' or 'size'");
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error");
            }
        });

        // Fetches proposed photocards
        app.get("/api/proposedphotocards", ctx -> {
            List<Photocard> proposedPhotocards = cardService.getProposedPhotocards();
            if (proposedPhotocards == null || proposedPhotocards.isEmpty()) {
                ctx.status(404);
            } else {
                ctx.status(200).json(proposedPhotocards);
            }
        });

        // Fetches all photocards with their types
        app.get("/api/allcardswithtype", ctx -> {
            List<Photocard> allPhotocards = cardService.getAllPcsWithType();
            if (allPhotocards == null || allPhotocards.isEmpty()) {
                ctx.status(404);
            } else {
                ctx.status(200).json(allPhotocards);
            }
        });

        // Fetches the group associated with an artist by artist ID
        app.get("/api/artist/{artistId}/group", ctx -> {
            try {
                int artistId = Integer.parseInt(ctx.pathParam("artistId"));

                String groupName = cardService.getArtistGroup(artistId);

                if (groupName != null) {
                    ctx.status(200).json(Map.of("group_name", groupName));
                } else {
                    ctx.status(404).result("Group not found for the given artist ID");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid artist ID. Please provide a valid numeric ID");
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error");
            }
        });

        // Fetches photocards for a specific group by group ID
        app.get("/api/groups/{groupId}/photocards", ctx -> {
            try {
                int groupId = Integer.parseInt(ctx.pathParam("groupId"));

                List<Photocard> photocards = cardService.getPhotocardsByGroup(groupId);

                if (photocards != null && !photocards.isEmpty()) {
                    ctx.status(200).json(photocards);
                } else {
                    ctx.status(404).result("No photocards found for the specified group");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid group ID. Please provide a valid numeric ID");
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error");
            }
        });

        // Fetches photocards for a specific search term
        app.get("/api/photocards/search", ctx -> {
            try {
                String searchTerm = ctx.queryParam("searchTerm");

                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    ctx.status(400).result("Search term cannot be empty.");
                    return;
                }

                List<Map<String, Object>> results = cardService.searchPhotocardsByTerm(searchTerm);

                if (results != null && !results.isEmpty()) {
                    ctx.status(200).json(results);
                } else {
                    ctx.status(404).result("No photocards found matching the search term");
                }
            } catch (Exception e) {
                ctx.status(500).result("Unexpected error occurred");
            }
        });



//        app.get("/api/artists/{groupsName}",ctx->{
//            String groupsName = ctx.pathParam("groupsName");
//            List<Artist> artists = cardService.getGroupArtists(groupsName);
//            if (artists != null && !artists.isEmpty()) {
//                ctx.status(200).json(artists);
//            } else {
//                ctx.status(404).result("Aucune photocard trouvée pour ce groupe");
//            }
//        });

    }
}