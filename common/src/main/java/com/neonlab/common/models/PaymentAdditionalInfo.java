package com.neonlab.common.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentAdditionalInfo {

    private String shortUrl;
    private String entityId;
    private String paymentLinkId;

    @Builder(builderMethodName = "buildPaymentAdditionalInfo")
    public PaymentAdditionalInfo(
            final String shortUrl,
            final String entityId,
            final String paymentLinkId
    ){
        this.shortUrl = shortUrl;
        this.entityId = entityId;
        this.paymentLinkId = paymentLinkId;
    }

}
