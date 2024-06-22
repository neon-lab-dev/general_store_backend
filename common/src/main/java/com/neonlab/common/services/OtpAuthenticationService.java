package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.dto.AuthenticationResponse;
import com.neonlab.common.enums.AuthType;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.utilities.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.neonlab.common.enums.CommunicationMode.PHONE;

@Slf4j
@Service
@Loggable
public class OtpAuthenticationService extends AbstractAuthenticationService{

    @Autowired private OtpService otpService;

    @Override
    public AuthType getAuthType() {
        return AuthType.OTP;
    }

    @Override
    public LoginResponse authenticate(AuthenticationRequest request)
            throws ServerException, InvalidInputException {
        validate(request);
        return otpService.verify(request);
    }

    @Override
    public void validate(AuthenticationRequest request) throws InvalidInputException {
        super.validate(request);
        if (StringUtil.isNullOrEmpty(request.getAuthCode())){
            throw new InvalidInputException("OTP is required.");
        }
        if (StringUtil.isNullOrEmpty(request.getPhone())){
            throw new InvalidInputException("Phone is required.");
        }
    }

}
