package com.ap.inventoryservice.controllers;

import com.ap.inventoryservice.services.InventoryService;
import com.ap.inventoryservice.to.ProductStockTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductStockTO> isInStock(@RequestParam List<String> productCodes){
        return inventoryService.isInStock(productCodes);
    }
}
