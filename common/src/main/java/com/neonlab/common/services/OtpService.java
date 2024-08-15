package com.neonlab.common.services;

import com.neonlab.common.config.ConfigurationKeys;
import com.neonlab.common.dto.*;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.CommunicationRequest;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.models.OtpSendResponse;
import com.neonlab.common.utilities.DateUtils;
import com.neonlab.common.entities.Otp;
import com.neonlab.common.repositories.otpRepository;
import com.neonlab.common.utilities.OtpUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

import static com.neonlab.common.enums.AuthStatus.*;
import static com.neonlab.common.enums.AuthStatus.EXPIRED;

@Slf4j
@Service
@NoArgsConstructor
public class OtpService {

    @Autowired private SystemConfigService systemConfigService;
    @Autowired private otpRepository otpRepository;
    @Autowired private UserService userService;
    @Autowired private SmsCommunicationService communicationService;

    @Autowired private AuthUserService authUserService;

    private static final String OTP_SENT = "OTP send successfully to %s";

    public OtpSendResponse send(AuthenticationRequest request) throws ServerException, InvalidInputException {
        Otp otpEntity = getOtpEntity(request);
        var communicationRequest = new CommunicationRequest(otpEntity);
        try {
            communicationService.send(communicationRequest);
        } catch (Exception e) {
            otpEntity.setStatus(FAILED);
            otpRepository.save(otpEntity);
            throw new ServerException("Error while sending sms : " + e.getMessage());
        }
        otpEntity.setStatus(SENT);
        otpRepository.save(otpEntity);
        return new OtpSendResponse(String.format(OTP_SENT, request.getPhone()), SENT);
    }

    private Otp getOtpEntity(AuthenticationRequest request) throws InvalidInputException {
        var retVal = new Otp(request.getPhone(), request.getPhone());
        retVal.setCommunicatedTo(request.getPhone());
        retVal.setOtp(getOtp());
        String expiryMinutes = systemConfigService.getSystemConfig(ConfigurationKeys.OTP_EXPIRY_SMS).getValue();
        retVal.setExpiryTime(DateUtils.getDateAfterNMinutes(Integer.parseInt(expiryMinutes)));
        return retVal;
    }

    private String getOtp() throws InvalidInputException {
        var staticOtpEnabled = isStaticOtpEnabled();
        if (staticOtpEnabled){
            return systemConfigService.getSystemConfig(ConfigurationKeys.STATIC_OTP).getValue();
        }
        return OtpUtil.generateOtp(4);
    }

    private boolean isStaticOtpEnabled(){
        try{
            return Boolean.parseBoolean(systemConfigService.getSystemConfig(ConfigurationKeys.STATIC_OTP_ENABLED).getValue());
        } catch (InvalidInputException e) {
            log.error(e.getMessage());
            return false;
        }
    }


    @Transactional
    public LoginResponse verify(AuthenticationRequest request) throws ServerException, InvalidInputException {
        String communicatedTo = request.getPhone();
        var otp = fetchOtpByCommunicatedToAndStatus(communicatedTo, SENT);
        if (isValid(otp, request)){
            otp.setStatus(VERIFIED);
            otp.setModifiedAt(new Date());
            otpRepository.save(otp);
            loginIfRequired(request);
            return getLoginResponse(request);
        }
       throw handleVerificationException(otp, request);
    }

    private void loginIfRequired(AuthenticationRequest request) {
        try {
            var authUser = authUserService.fetchInactiveByUsername( request.getPhone());
            authUser.setActive(true);
            authUserService.save(authUser);
        } catch (InvalidInputException e) {
            log.info(e.getMessage());
        }
    }

    private LoginResponse getLoginResponse(AuthenticationRequest request) throws InvalidInputException {
        var existingUser = userService.isExistingUser(request.getPhone());
        var retVal = new LoginResponse(VERIFIED, existingUser);
        if (existingUser){
            var authUser = authUserService.fetchActiveByUsername(request.getPhone());
            retVal.setToken(authUser.getToken());
        }
        return retVal;
    }

    private ServerException handleVerificationException(Otp otp, AuthenticationRequest request) throws ServerException {
        if (otp.getExpiryTime().before(new Date())){
            otp.setModifiedAt(new Date());
            otp.setStatus(EXPIRED);
            otpRepository.save(otp);
            return new ServerException("Otp Expired. Please re-send new otp.");
        }
        if (!Objects.equals(request.getAuthCode(), otp.getOtp())){
            return new ServerException("Invalid Otp. Please enter correct Otp.");
        }
        throw new ServerException("Undefined Exception");
    }

    private boolean isValid(Otp otp, AuthenticationRequest request){
        return (otp.getExpiryTime().after(new Date()) && Objects.equals(request.getAuthCode(), otp.getOtp()));
    }

    public Otp fetchOtpByCommunicatedToAndStatus(String communicatedTo
            , AuthStatus status) throws ServerException {
        var retVal = otpRepository.findFirstByCommunicatedToAndStatusOrderByCreatedAtDesc(communicatedTo, status);
        if (retVal.isPresent()){
            return retVal.get();
        }
        throw new ServerException(String.format("No Otp record found with communication to as %s and status as %s."
                , communicatedTo, status));
    }
}
