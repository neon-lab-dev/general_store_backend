package com.neonlab.common.models.razorpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookBody {

    private List<String> contains;
    private String event;
    private Payload payload;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payload{

        @JsonProperty("payment_link")
        private Map<String, CreatePaymentLinkModel> paymentLink;

    }

}
