package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.SignUpRequest;
import com.neonlab.common.entities.Otp;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.repositories.otpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class AuthRecordService {

    private final otpRepository otpRepository;


    public Otp create(SignUpRequest request){
        var retVal = new Otp();
        retVal.setStatus(AuthStatus.TO_BE_VERIFIED);
        retVal = otpRepository.save(retVal);
        return retVal;
    }

    public Otp fetchById(String authId) throws InvalidInputException {
        var retVal = otpRepository.findById(authId);
        return retVal.orElseThrow(() -> new InvalidInputException("AuthRecord not found with id "+authId));

    }

}
