package org.souaimane.invantoryservice;

import org.souaimane.invantoryservice.entities.Inventory;
import org.souaimane.invantoryservice.repository.InventoryRepository;
import org.souaimane.invantoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InvantoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvantoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(InventoryRepository inventoryRepository){
        return args -> {
           /* for (int i = 0; i <30 ; i++) {
                inventoryRepository.save(new Inventory(null, UUID.randomUUID().toString(),((int) (Math.random() * 100))));
            }*/
            inventoryRepository.save(new Inventory(null, "iphone",2));

        };


    }

}
