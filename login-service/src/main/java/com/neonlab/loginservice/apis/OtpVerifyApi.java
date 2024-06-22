package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.enums.AuthType;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.services.AuthenticationServiceProvider;
import com.neonlab.common.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
public class OtpVerifyApi {

    @Autowired private AuthenticationServiceProvider serviceProvider;

    @Autowired private UserService userService;

    private static final String OTP_VERIFIED = "OTP verified successfully for %s";

    public ApiOutput<LoginResponse> verify(AuthenticationRequest request) throws InvalidInputException {
        var authenticationService = serviceProvider.getService(AuthType.OTP);
        try {
            var retVal = authenticationService.authenticate(request);
            return new ApiOutput<>(HttpStatus.OK.value(), String.format(OTP_VERIFIED, request.getPhone()), retVal);
        } catch (ServerException e) {
            var existingUser = userService.isExistingUser(request.getPhone());
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),new LoginResponse(null, existingUser));
        }
    }

}
