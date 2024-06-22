package com.neonlab.common.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.config.ConfigurationKeys;
import com.neonlab.common.enums.PaymentProvider;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.PaymentRequest;
import com.neonlab.common.models.PaymentResponse;
import com.neonlab.common.models.razorpay.WebhookPaymentModel;
import com.neonlab.common.utilities.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@Loggable
public abstract class AbstractPaymentService implements PaymentService {

    @Autowired private ValidationUtils validationUtils;
    @Autowired protected PaymentRecordService paymentRecordService;
    @Autowired protected UserService userService;
    @Autowired protected SystemConfigService systemConfigService;

    @Override
    public abstract PaymentProvider getProvider();

    @Override
    public abstract PaymentResponse createPayment(PaymentRequest request) throws InvalidInputException, ServerException, JsonParseException;

    @Override
    public abstract void validateWebhook(WebhookPaymentModel request) throws NoSuchAlgorithmException, InvalidKeyException, InvalidInputException, JsonParseException;

    @Override
    public void validatePaymentRequest(PaymentRequest request){
        validationUtils.validate(request);
    }

    protected Integer getExpireAfter() throws InvalidInputException {
        var value = systemConfigService.getSystemConfig(ConfigurationKeys.PAYMENT_VALIDITY).getValue();
        return Integer.parseInt(value);
    }
}
