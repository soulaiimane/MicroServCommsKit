package org.souaimane.invantoryservice.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.souaimane.invantoryservice.dto.InventoryResponse;
import org.souaimane.invantoryservice.entities.Inventory;
import org.souaimane.invantoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private InventoryRepository inventoryRepository;
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode)  {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                            InventoryResponse.builder()
                                    .skuCode(inventory.getSkuCode())
                                    .isInStock(inventory.getQuantity()>0)
                                    .build()
                        ).toList();


    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }
}