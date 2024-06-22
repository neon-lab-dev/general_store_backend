package com.neonlab.common.models;

import com.neonlab.common.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentUpdateModel {

    private String id;
    private PaymentStatus status;
    private String additionalInfo;
    private String externalId;

    @Builder(builderMethodName = "buildPaymentUpdateModel")
    public PaymentUpdateModel(
            final String id,
            final PaymentStatus paymentStatus,
            final String additionalInfo,
            final String externalId
    ){
        this.id = id;
        this.status = paymentStatus;
        this.additionalInfo = additionalInfo;
        this.externalId = externalId;
    }

}
