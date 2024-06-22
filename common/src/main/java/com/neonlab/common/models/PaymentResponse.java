package com.neonlab.common.models;

import com.neonlab.common.entities.Payment;
import com.neonlab.common.enums.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentResponse {

    private String paymentId;
    private BigDecimal amount;
    private String description;
    private PaymentStatus paymentStatus;
    private PaymentAdditionalInfo additionalInfo;

    public PaymentResponse(Payment payment, PaymentAdditionalInfo additionalInfo){
        this.paymentId = payment.getId();
        this.amount = payment.getAmount();
        this.description = payment.getDescription();
        this.paymentStatus = payment.getStatus();
        this.additionalInfo = additionalInfo;

    }

}
