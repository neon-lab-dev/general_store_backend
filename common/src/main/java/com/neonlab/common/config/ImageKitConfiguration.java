package com.neonlab.common.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.SystemConfigService;
import com.neonlab.common.utilities.JsonUtils;
import io.imagekit.sdk.ImageKit;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Slf4j
@Configuration
public class ImageKitConfiguration {

    @Autowired private SystemConfigService systemConfigService;

    private ImageKitProperties properties;

    @PostConstruct
    void setup(){
        log.info("Setting up document service provider ImageKit.io");
        try {
            var configJson = systemConfigService.getSystemConfig(ConfigurationKeys.IMAGE_KIT_CONFIG).getValue();
            properties = JsonUtils.readObjectFromJson(configJson, ImageKitProperties.class);

        } catch (InvalidInputException | JsonParseException e) {
            log.error("Failed to setup ImageKit.io with error {}.", e.getMessage());
        }
    }

    @Bean
    public ImageKit imageKit(){
        ImageKit imagekit = ImageKit.getInstance();
        if (Objects.nonNull(properties)) {
            var config = new io.imagekit.sdk.config.Configuration(
                    properties.getUrlEndpoint(),
                    properties.getPrivateKey(),
                    properties.getPublicKey()
            );
            imagekit.setConfig(config);
            log.info("Service provider configured successfully.");
            return imagekit;
        }
        return imagekit;
    }

}
