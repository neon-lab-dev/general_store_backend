package com.neonlab.common.services;

import com.neonlab.common.enums.CommunicationMode;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.models.CommunicationRequest;
import org.springframework.stereotype.Service;

@Service
public interface CommunicationService {

    CommunicationMode getVerificationMode();

    boolean send(CommunicationRequest request) throws InvalidInputException;

    void validate(CommunicationRequest request) throws InvalidInputException;

}
