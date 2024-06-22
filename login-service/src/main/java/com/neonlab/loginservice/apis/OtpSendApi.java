package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.OtpSendResponse;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.StringUtil;
import com.neonlab.common.services.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class OtpSendApi {

    private final OtpService otpService;

    public ApiOutput<OtpSendResponse> send(AuthenticationRequest request) throws InvalidInputException {
        validateRequest(request);
        try {
            return new ApiOutput<>(HttpStatus.OK.value(), null, otpService.send(request));
        } catch (ServerException e){
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), null ,new OtpSendResponse(e.getMessage(), AuthStatus.FAILED));
        }
    }

    //ToDO: create these in Abstract Api
    private void validateRequest(AuthenticationRequest authenticationRequest) throws InvalidInputException {
        if(StringUtil.isNullOrEmpty(authenticationRequest.getPhone())){
            throw new InvalidInputException("Phone is Required");
        }
    }

}
