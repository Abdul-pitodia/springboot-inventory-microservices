package com.ap.inventoryservice.services;

import com.ap.inventoryservice.model.Inventory;
import com.ap.inventoryservice.repository.InventoryRepository;
import com.ap.inventoryservice.to.ProductStockTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<ProductStockTO> isInStock(List<String> productCodes){
        List<Inventory> inventoryList = inventoryRepository.findByProductCodeIn(productCodes);
//
//        log.info("starting delay");
//        Thread.sleep(5000);
//        log.info("ended delay");

        Function<Inventory, ProductStockTO> mapToProductStockTO = (inventory) -> {
            return new ProductStockTO(inventory.getProductCode(), inventory.getQuantity() > 0);
        };

        return inventoryList.stream().map(mapToProductStockTO).toList();
    }


}
