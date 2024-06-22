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
public class TwoFactorConfiguration {

    @Autowired private SystemConfigService systemConfigService;

    private SmsServiceProviderProperties properties;

    @PostConstruct
    void setup(){
        log.info("Setting up otp service provider 2Factor");
        try {
            var configJson = systemConfigService.getSystemConfig(ConfigurationKeys.OTP_PROVIDER_CONFIG).getValue();
            properties = JsonUtils.readObjectFromJson(configJson, SmsServiceProviderProperties.class);
        } catch (InvalidInputException | JsonParseException e) {
            log.error("Failed to setup 2Factor with error {}.", e.getMessage());
            log.info("Setting Dummy value in property");
            properties = new SmsServiceProviderProperties("Dummy","Dummy");
        }
    }

    @Bean
    public SmsServiceProviderProperties smsProviderProperties(){
        return properties;
    }


}
