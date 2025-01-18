package com.example.appdai;

import com.example.appdai.controller.GroupController;
import com.example.appdai.controller.PcController;
import com.example.appdai.controller.UserController;
import com.example.appdai.service.GroupService;
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

    @Autowired
    public AppDaiApplication(PcService pcService, GroupService groupService, UserService userService) {
        this.pcService = pcService;
        this.groupService = groupService;
        this.userService = userService;
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

        pcController.registerRoutes(app);
        groupController.registerRoutes(app);
        userController.registerRoutes(app);

        app.start(7070);
    }
}
