package com.neonlab.common.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotNull(message = "Amount is mandatory")
    private BigDecimal amount;
    private String description;
    @NotNull(message = "Please specify Online or Offline Payment")
    private Boolean online = true;
    @NotNull(message = "Please specify if standard link or upi")
    private Boolean upiLink = false;

}
