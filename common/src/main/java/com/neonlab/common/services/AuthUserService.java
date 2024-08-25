package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.LoginDto;
import com.neonlab.common.dto.SignUpRequest;
import com.neonlab.common.entities.AuthUser;
import com.neonlab.common.entities.User;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.repositories.AuthUserRepository;
import com.neonlab.common.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.neonlab.common.enums.AuthStatus.VERIFIED;

@Slf4j
@Service
@Loggable
public class AuthUserService implements UserDetailsService {

    @Autowired private AuthUserRepository authUserRepository;
    @Autowired private UserRepository userRepository;

    public AuthUser createAuthUser(SignUpRequest request, User user){
        var retVal = new AuthUser(request.getPrimaryPhoneNo(), request.getPrimaryPhoneNo());
        retVal.setUserName(request.getPrimaryPhoneNo());
        retVal.setActive(true);
        retVal.setToken(UUID.randomUUID().toString());
        retVal.setUserId(user.getId());
        retVal = authUserRepository.save(retVal);
        return retVal;
    }

    public void delete(String userId) throws InvalidInputException {
        var authUser = fetchLatestByUserId(userId);
        authUserRepository.delete(authUser);
    }

    public AuthUser fetchById(String id) throws InvalidInputException {
        return authUserRepository.findById(id).orElseThrow(() ->
                new InvalidInputException(String.format("AuthUser not found with id %s.",id)));
    }

    public AuthUser fetchLatestByUserId(String userId) throws InvalidInputException {
        return authUserRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidInputException(String.format("AuthUser not found with userId %s", userId)));
    }

    public AuthUser fetchActiveByUsername(String username) throws InvalidInputException {
        return authUserRepository.findActiveByUsernameAndDeviceId(username)
                .orElseThrow(() -> new InvalidInputException
                        (String.format("Active AuthUser not found with username %s", username)));
    }

    public Long fetchActiveUserCount(){
        return authUserRepository.countByActive(true);
    }

    public AuthUser fetchActiveByToken(String token) throws InvalidInputException {
        return authUserRepository.findActiveByToken(token)
                .orElseThrow(() -> new InvalidInputException
                        (String.format("Active AuthUser not found with token %s", token)));
    }

    public AuthUser getActiveByToken(String token) {
        AuthUser retVal = null;
        try {
            retVal = fetchActiveByToken(token);
        } catch (InvalidInputException e) {
            log.warn(e.getMessage());
        }
        return retVal;
    }

    public AuthUser fetchInactiveByUsername(String username) throws InvalidInputException {
        return authUserRepository.findInactiveByUsername(username)
                .orElseThrow(() -> new InvalidInputException
                        (String.format("Inactive AuthUser not found with username %s", username)));
    }

    public AuthUser save(AuthUser authUser){
        return authUserRepository.save(authUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var auth = fetchActiveByToken(username);
        } catch (InvalidInputException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return null;
    }

    public LoginResponse login(LoginDto request) throws InvalidInputException, ServerException {
        var user = fetchUserByPrimaryPhoneNoOrEmail(request.getUserName(),request.getUserName());
        var authUser = fetchLatestByUserId(user.getId());
        if(authUser.getPassword().equals(request.getPassword())){
            authUser.setActive(true);
            authUserRepository.save(authUser);
            return new LoginResponse(authUser.getToken(), true, VERIFIED);
        }
        throw new InvalidInputException("Please enter valid User name or password");
    }

    public User fetchUserByPrimaryPhoneNoOrEmail(String phone, String email) throws InvalidInputException {
        return userRepository.findByPrimaryPhoneNoOrEmail(phone, email)
                .orElseThrow(() -> new InvalidInputException(String.format("User not found with primary Phone no %s or email %s", phone, email)));
    }
    public boolean isUserActive(AuthUser authUser){
        return authUser.isActive();
    }
}
