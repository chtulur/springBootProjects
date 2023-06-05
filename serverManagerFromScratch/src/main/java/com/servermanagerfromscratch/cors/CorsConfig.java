package com.servermanagerfromscratch.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:4200", "http://localhost:4200")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .exposedHeaders("Authorization")  // Optional: Expose any additional headers if needed
            .allowCredentials(true)  // Optional: Enable credentials (e.g., cookies) to be sent with requests
            .maxAge(3600);  // Optional: Set the maximum age of the preflight request cache
    System.out.println("CORS configuration is applied."); // Add this line for debugging purposes
  }
}

