package com.neonlab.product.models.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailData {
    private String name;
    private String orderNumber;
    private String orderDate;
    private String items;
    private BigDecimal totalAmount;
    private String email;
    private String phone;
    private String previousStatus;
    private String updatedStatus;
    private  String estimatedDeliveryDate;
}
