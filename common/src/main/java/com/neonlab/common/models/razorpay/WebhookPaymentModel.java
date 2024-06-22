package com.neonlab.common.models.razorpay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookPaymentModel {

    private String signature;
    private String eventId;
    private String body;

}
