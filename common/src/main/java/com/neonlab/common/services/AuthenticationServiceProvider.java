package com.neonlab.common.services;

import com.neonlab.common.enums.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AuthenticationServiceProvider {

    @Autowired private ApplicationContextProvider applicationContextProvider;

    public AuthenticationService getService(AuthType authType){
        return Objects.equals(authType, AuthType.OTP) ?
                applicationContextProvider.getApplicationContext().getBean(OtpAuthenticationService.class):
                applicationContextProvider.getApplicationContext().getBean(PasswordAuthenticationService.class);
    }

}
