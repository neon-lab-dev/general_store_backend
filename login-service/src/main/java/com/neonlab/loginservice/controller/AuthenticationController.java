package com.neonlab.loginservice.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.AuthenticationRequest;
import com.neonlab.common.dto.LoginDto;
import com.neonlab.common.dto.SignUpRequest;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.loginservice.apis.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Loggable
public class AuthenticationController {

    private final OtpSendApi otpSendApi;
    private final OtpVerifyApi otpVerifyApi;
    private final SignUpApi signUpApi;
    private final LogoutApi logoutApi;
    private final LoginApi loginApi;


    @PostMapping("/send-otp")
    public ApiOutput<?> sendOtp(@RequestBody AuthenticationRequest request) throws InvalidInputException {
        return otpSendApi.send(request);
    }


    @PostMapping("/verify-otp")
    public ApiOutput<?> verifyAccount(@RequestBody AuthenticationRequest request)
            throws InvalidInputException {
        return otpVerifyApi.verify(request);
    }

    @PostMapping("/signup")
    public ApiOutput<?> signup(@RequestBody SignUpRequest request) throws InvalidInputException, ServerException {
        return signUpApi.process(request);
    }

    @PostMapping("/login")
    public ApiOutput<?> login(@RequestBody LoginDto request){
        return loginApi.process(request);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiOutput<?> logout(){
        return logoutApi.logout();
    }


}
