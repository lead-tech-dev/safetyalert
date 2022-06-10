package com.safetynet.alert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * AlertApplication. Main application class
 */
@SpringBootApplication
public class AlertApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlertApplication.class, args);

  }

  @Bean
  CommandLineRunner runner() {
    return args -> {
      System.out.println("Welcome to safetynet app");
    };
  }
}
