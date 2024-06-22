package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.enums.CommunicationMode;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.models.CommunicationRequest;
import com.neonlab.common.utilities.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@Loggable
public abstract class AbstractCommunicationService implements CommunicationService{
    @Override
    public abstract CommunicationMode getVerificationMode();

    @Override
    public void validate(CommunicationRequest request) throws InvalidInputException {
        if (Objects.isNull(request)){
            throw new InvalidInputException("Communication request cannot be null.");
        }
        if (StringUtil.isNullOrEmpty(request.getTo())){
            throw new InvalidInputException("Receiver details is mandatory.");
        }
        if (Objects.isNull(request.getBody())){
            throw new InvalidInputException("Communication body is empty");
        }
    }

    @Override
    public abstract boolean send(CommunicationRequest request) throws InvalidInputException;

    public static CommunicationService getService(CommunicationMode mode){
        return Objects.equals(mode, CommunicationMode.PHONE) ?
                null : EmailCommunicationService.get();
    }

}
