package com.neonlab.loginservice.apis;

import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.SignUpRequest;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.utilities.StringUtil;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.loginservice.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApi {

    private final SignupService signUpService;

    private static final String SUCCESS = "User %s signed up successfully";

    public ApiOutput<LoginResponse> process(SignUpRequest request) throws InvalidInputException, ServerException {
        validate(request);
        return new ApiOutput<>(HttpStatus.OK.value(),
                String.format(SUCCESS, request.getPrimaryPhoneNo()),
                signUpService.registerUser(request));
    }

    private void validate(SignUpRequest request) throws InvalidInputException {
        if (StringUtil.isNullOrEmpty(request.getName())){
            throw new InvalidInputException("Name of the user is mandatory.");
        }
        if (StringUtil.isNullOrEmpty(request.getEmail())){
            throw new InvalidInputException("Email of the user is mandatory.");
        }
        if (StringUtil.isNullOrEmpty(request.getPrimaryPhoneNo())){
            throw new InvalidInputException("Primary Phone number of the user is mandatory.");
        }
        if (signUpService.isAlreadySignedUp(request)){
            throw new InvalidInputException("User already exists with same phone and/or email.");
        }

    }

}
