package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.entities.SystemConfig;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.repositories.SystemConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigService {
    
    private final SystemConfigRepository systemConfigRepository;


    /**
     * Fetches the configuration value for the give configuration key
     * 
     * @param configKey configuration key
     * @return System Config entity
     * @throws InvalidInputException in case no entity found with the defined key
     */
    public SystemConfig getSystemConfig(String configKey) throws InvalidInputException {
        SystemConfig systemConfig = systemConfigRepository.findByKey(configKey);
        if (Objects.isNull(systemConfig)) {
            throw new InvalidInputException("No Config defined in system config for key " + configKey);
        }
        return systemConfig;
    }
    
}
