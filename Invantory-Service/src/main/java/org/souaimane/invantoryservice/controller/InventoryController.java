package org.souaimane.invantoryservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.souaimane.invantoryservice.dto.InventoryResponse;
import org.souaimane.invantoryservice.entities.Inventory;
import org.souaimane.invantoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor

public class InventoryController {
    InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);

    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findAll(){

        return inventoryService.findAll();

    }

}
