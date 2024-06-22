package com.neonlab.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorPayProperties {

    private String apiKeyId;
    private String apiKeySecret;
    private String paymentLinkBaseUrl;
    private String webhookSecret;

}
