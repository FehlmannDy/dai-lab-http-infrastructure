package com.example.appdai.config;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Le package config est utilisé pour regrouper les classes de configuration, comme celles qui gèrent :

    Les paramètres de Javalin.
    Les configurations Spring Boot spécifiques (sécurité, base de données, etc.).
    Les beans partagés dans l'application.
 */
@Configuration
public class JavalinConfig {
    @Bean
    public Javalin javalin() {
        return Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                // Autorise toutes les origines (CORS ouvert) que pendant le development
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
//                On remplacera par ça quand on aura setup le frontend
//                cors.addRule(it -> {
//                    it.allowHost("https://example.com", "https://trading-kpop.io");
//                });
            });


        });
    }

}
