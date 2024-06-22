package com.neonlab.common.config;


import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.SystemConfigService;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RazorPayConfiguration {

    @Autowired private SystemConfigService systemConfigService;

    private RazorPayProperties properties;

    @PostConstruct
    void setup(){
        log.info("Setting up Payment service provider RazorPay");
        try {
            var configJson = systemConfigService.getSystemConfig(ConfigurationKeys.PAYMENT_SERVICE_PROVIDER_CONFIG).getValue();
            properties = JsonUtils.readObjectFromJson(configJson, RazorPayProperties.class);
        } catch (InvalidInputException | JsonParseException e) {
            log.error("Failed to setup RazorPay with error, {}", e.getMessage());
            log.info("Setting RazorPay with dummy values");
            properties = new RazorPayProperties(null, null, null, null);
        }
    }

    @Bean
    public RazorPayProperties razorPayProperties(){
        return properties;
    }

}
