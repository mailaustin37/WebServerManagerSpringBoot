package com.influence.webservermanagement;

import com.influence.webservermanagement.model.Server;
import com.influence.webservermanagement.model.Status;
import com.influence.webservermanagement.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class WebserverManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebserverManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository){
        return args -> {
            if(serverRepository.findById(1L).isEmpty()) {
               /* serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB", "Personal PC", "server1.png", Status.SERVER_DOWN));
                serverRepository.save(new Server(null, "142.251.39.110", "Google", "Unlimited", "Website", "server2.png", Status.SERVER_UP));
                serverRepository.save(new Server(null, "157.240.201.35", "Facebook", "Unlimited", "Website", "server3.png", Status.SERVER_UP));*/
            }
        };
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200/","http://localhost:3000/"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Origin","Access-Control-Allow-Origin","Content-Type","Accept","Jwt-Token","Authorization","X-Requested-With",
                "Access-Control-Request-Method","Access-Control-Request-Headers"
        ));
        corsConfiguration.setExposedHeaders(Arrays.asList(
                "Origin","Access-Control-Allow-Origin","Content-Type","Accept","Jwt-Token","Authorization","X-Requested-With",
                "Filename","Access-Control-Allow-Credentials"
        ));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);


    }

}