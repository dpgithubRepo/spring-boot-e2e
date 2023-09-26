package com.dp.inventoryservice.service;

import com.dp.inventoryservice.model.Inventory;
import com.dp.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Boolean isInStock(String skuCode){
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(skuCode);
        return inventoryOptional.isPresent();
    }

}
