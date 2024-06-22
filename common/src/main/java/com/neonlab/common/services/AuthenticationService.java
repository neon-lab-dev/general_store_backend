package com.neonlab.common.services;

import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.dto.AuthenticationResponse;
import com.neonlab.common.enums.AuthType;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthType getAuthType();

    LoginResponse authenticate(AuthenticationRequest request) throws ServerException, InvalidInputException;

    void validate(AuthenticationRequest request)throws InvalidInputException;


}
