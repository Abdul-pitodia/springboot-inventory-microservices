package com.ap.orderservice.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemTO {

    private Long id;
    private  String productCode;
    private BigDecimal price;
    private Integer quantity;
}
