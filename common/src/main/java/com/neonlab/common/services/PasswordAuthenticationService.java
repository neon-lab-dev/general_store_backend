package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.entities.Otp;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.enums.AuthType;
import com.neonlab.common.enums.VerificationPurpose;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.repositories.otpRepository;
import com.neonlab.common.utilities.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
public class PasswordAuthenticationService extends AbstractAuthenticationService{

    @Autowired private otpRepository otpRepository;

    @Override
    public AuthType getAuthType() {
        return AuthType.PASSWORD;
    }

    @Override
    public LoginResponse authenticate(AuthenticationRequest request) {
        return null;
    }

    @Override
    public void validate(AuthenticationRequest request) throws InvalidInputException {
        if (StringUtil.isNullOrEmpty(request.getAuthCode())){
            throw new InvalidInputException("Password is required.");
        }
    }

    private Otp fetchOtpByCommunicatedToAndStatusAndPurpose(String communicatedTo
            , AuthStatus status, VerificationPurpose purpose) throws ServerException{
        var retVal = otpRepository.findFirstByCommunicatedToAndStatusOrderByCreatedAtDesc(communicatedTo, status);
        if (retVal.isPresent()){
            return retVal.get();
        }
        throw new ServerException(String.format("Password not set for the user %s.", communicatedTo));
    }

}
