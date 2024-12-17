package com.example.appdai;

import com.example.appdai.controller.PcController;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.javalin.Javalin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class
})
 */
@SpringBootApplication
public class AppDaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDaiApplication.class, args);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));
        });

        // Enregistre les routes via PcController
        PcController pcController = new PcController(app);
        pcController.registerRoutes(app);

        app.start(7070);
    }
}
