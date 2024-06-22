package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.dto.AuthenticationResponse;
import com.neonlab.common.enums.AuthType;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@Loggable
public abstract class AbstractAuthenticationService implements AuthenticationService {


    @Override
    public abstract AuthType getAuthType();

    @Override
    public abstract LoginResponse authenticate(AuthenticationRequest request)
            throws ServerException, InvalidInputException;

    @Override
    public void validate(AuthenticationRequest request) throws InvalidInputException {
        /*if (Objects.isNull(request.getType())){
            throw new InvalidInputException("AuthType is required.");
        }
        if (Objects.isNull(request.getPurpose())){
            throw new InvalidInputException("Purpose is required.");
        }*/
    }

}
