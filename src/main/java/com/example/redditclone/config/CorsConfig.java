//package com.example.redditclone.config;
//
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//@AutoConfiguration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("*")
//                .maxAge(3600L)
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true);
//    }
//}
