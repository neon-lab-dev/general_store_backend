package com.neonlab.common.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.entities.Payment;
import com.neonlab.common.enums.PaymentProvider;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.PaymentRequest;
import com.neonlab.common.models.PaymentResponse;
import com.neonlab.common.models.razorpay.WebhookPaymentModel;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public interface PaymentService {

    PaymentProvider getProvider();

    PaymentResponse createPayment(PaymentRequest request) throws InvalidInputException, ServerException, JsonParseException;

    void validateWebhook(WebhookPaymentModel request) throws NoSuchAlgorithmException, InvalidKeyException, InvalidInputException, JsonParseException;

    void processWebhook(WebhookPaymentModel request) throws JsonParseException, InvalidInputException, ServerException;

    Payment fetchPaymentStatus(String id) throws InvalidInputException, JsonParseException, ServerException;

    void validatePaymentRequest(PaymentRequest request);

}
