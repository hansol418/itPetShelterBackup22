package com.itpetshelter.itpetshelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ItPetShelterBackup22Application {

    public static void main(String[] args) {
        SpringApplication.run(ItPetShelterBackup22Application.class, args);
    }

}
