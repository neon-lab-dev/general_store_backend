package com.neonlab.loginservice.service;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.SignUpRequest;
import com.neonlab.common.entities.AuthUser;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.services.AuthUserService;
import com.neonlab.common.services.UserService;
import com.neonlab.common.models.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.neonlab.common.constants.GlobalConstants.ERROR_OCCURRED;
import static com.neonlab.common.enums.AuthStatus.VERIFIED;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class SignupService {

    private static final String LOGOUT_MSG="User has been logged out successfully";

    private final UserService userService;
    private final AuthUserService authUserService;

    public LoginResponse registerUser(SignUpRequest request) throws ServerException {
        var user = userService.createUser(request);
        var authUser = authUserService.createAuthUser(request, user);
        authUserService.save(authUser);
        return getLoginResponse(authUser, request);
    }
    
    private LoginResponse getLoginResponse(AuthUser authUser, SignUpRequest request){
        var existingUser = userService.isExistingUser(request.getPrimaryPhoneNo());
        var retVal = new LoginResponse(authUser.getToken(), existingUser, VERIFIED);
        return retVal;
    }
    

    public boolean isAlreadySignedUp(SignUpRequest request){
        try{
            var user = userService.fetchByPrimaryPhoneNoOrEmail(request.getPrimaryPhoneNo(), request.getEmail());
            return true;
        } catch (InvalidInputException e) {
            log.warn(ERROR_OCCURRED, e.getMessage());
            return false;
        }
    }

    public String logout() throws InvalidInputException {
        String username=userService.getLoggedInUser().getPrimaryPhoneNo();
        AuthUser authUser= authUserService.fetchActiveByUsername(username);
        authUser.setActive(false);
        authUserService.save(authUser);
        return LOGOUT_MSG;
    }


}
