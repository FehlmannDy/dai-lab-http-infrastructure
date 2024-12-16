package com.example.appdai.config;

import io.javalin.Javalin;
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
        // cest juste pour remplir mais normalement on doit configurer CORS, etc
        // en gros plus tard on autorisera que les requetes venant de notre frontend
        return Javalin.create();
    }
}
