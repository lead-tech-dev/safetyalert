package com.safetynet.alert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.io.File;

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
