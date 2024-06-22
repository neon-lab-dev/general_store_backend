package com.neonlab.common.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentStatusRequest {

    private String paymentId;
    private Boolean external = false;

}
