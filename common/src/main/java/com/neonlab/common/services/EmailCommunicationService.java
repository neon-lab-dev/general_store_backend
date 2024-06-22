package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.enums.CommunicationMode;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.models.CommunicationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
public class EmailCommunicationService extends AbstractCommunicationService{

    private static final EmailCommunicationService onlyService = new EmailCommunicationService();

    public static EmailCommunicationService get(){
        return onlyService;
    }

    @Override
    public CommunicationMode getVerificationMode() {
        return CommunicationMode.EMAIL;
    }

    @Override
    public boolean send(CommunicationRequest request) throws InvalidInputException {
        validate(request);
        return true;
    }

    @Override
    public void validate(CommunicationRequest request) throws InvalidInputException {
        super.validate(request);
    }
}
