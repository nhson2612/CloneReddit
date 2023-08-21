package com.example.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("nhtrungddab@gmail.com");
        javaMailSender.setPassword("son26122004");
        javaMailSender.setPort(567);

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTION","DELETE","UPDATE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//    @Bean
//    public WebMvcConfigurer corsConfig(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("*")
//                        .maxAge(3600L)
//                        .allowedHeaders("*")
//                        .exposedHeaders("Authorization")
//                        .allowCredentials(true);
//            }
//        };
//    }

}
