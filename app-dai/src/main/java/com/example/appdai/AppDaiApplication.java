package com.example.appdai;

import com.example.appdai.controller.PcController;
import com.example.appdai.service.PcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.javalin.Javalin;

@SpringBootApplication
public class AppDaiApplication implements CommandLineRunner {

    private final PcService pcService;

    @Autowired
    public AppDaiApplication(PcService pcService) {
        this.pcService = pcService;
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
        pcController.registerRoutes(app);

        app.start(7070);
    }
}
