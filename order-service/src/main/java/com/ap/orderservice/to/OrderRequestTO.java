package com.ap.orderservice.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestTO {

    private String userEmail;

    private List<OrderItemTO> orderItemToList;
}
