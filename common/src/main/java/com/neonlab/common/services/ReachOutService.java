package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.config.ConfigurationKeys;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.utilities.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Loggable
@RequiredArgsConstructor
public class ReachOutService {

    private final SystemConfigService configService;

    public String getIndexPage() throws InvalidInputException {
        var filePath = configService.getSystemConfig(ConfigurationKeys.REACH_OUT_HTML_PATH).getValue();
        return FileUtils.getFileContent(filePath);
    }

}
