package com.ap.orderservice.to;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductStockTO {

    private String productCode;
    private boolean inStock;
}
