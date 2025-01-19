package com.example.appdai;

import com.example.appdai.controller.GroupController;
import com.example.appdai.controller.OfController;
import com.example.appdai.controller.PcController;
import com.example.appdai.controller.UserController;
import com.example.appdai.service.GroupService;
import com.example.appdai.service.OfService;
import com.example.appdai.service.PcService;
import com.example.appdai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.javalin.Javalin;

@SpringBootApplication
public class AppDaiApplication implements CommandLineRunner {

    private final PcService pcService;
    private final GroupService groupService;
    private final UserService userService;
    private final OfService ofService;

    @Autowired
    public AppDaiApplication(PcService pcService, GroupService groupService, UserService userService, OfService ofService) {
        this.pcService = pcService;
        this.groupService = groupService;
        this.userService = userService;
        this.ofService = ofService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppDaiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));
        });

        PcController pcController = new PcController(app, pcService);
        GroupController groupController = new GroupController(app, groupService);
        UserController userController = new UserController(app, userService);
        OfController ofController = new OfController(app, ofService);

        pcController.registerRoutes(app);
        groupController.registerRoutes(app);
        userController.registerRoutes(app);
        ofController.registerRoutes(app);

        app.start(7070);
    }
}
