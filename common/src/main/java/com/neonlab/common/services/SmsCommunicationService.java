package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.config.SmsServiceProviderProperties;
import com.neonlab.common.enums.CommunicationMode;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.models.CommunicationRequest;
import com.neonlab.common.models.RestRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@Loggable
public class SmsCommunicationService extends AbstractCommunicationService {

    @Autowired private SmsServiceProviderProperties properties;
    @Autowired private WebfluxRestRequestService webfluxRestRequestService;

    @Override
    public CommunicationMode getVerificationMode() {
        return CommunicationMode.PHONE;
    }

    @Override
    public boolean send(CommunicationRequest request) throws InvalidInputException {
        validate(request);
        var url = UriComponentsBuilder
                .fromHttpUrl(String.format(properties.getVoiceOtpUrl(), properties.getApiKey(), request.getTo(),request.getBody()))
                .build()
                .toUriString();
        var retVal = webfluxRestRequestService.get(RestRequestModel.buildRestRequest()
                        .url(url)
                        .method(HttpMethod.GET)
                .build());
        log.info(retVal);
        return true;
    }

    @Override
    public void validate(CommunicationRequest request) throws InvalidInputException {
        super.validate(request);
    }

}
