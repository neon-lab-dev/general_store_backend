package com.neonlab.common.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.entities.Payment;
import com.neonlab.common.entities.User;
import com.neonlab.common.enums.PaymentProvider;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.*;
import com.neonlab.common.models.razorpay.WebhookPaymentModel;
import com.neonlab.common.repositories.PaymentRepository;
import com.neonlab.common.utilities.JsonUtils;
import com.neonlab.common.utilities.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

import static com.neonlab.common.enums.PaymentStatus.INITIATED;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class PaymentRecordService {

    private final PaymentRepository paymentRepository;
    private final PaymentServiceProvider serviceProvider;

    public Payment initiate(PaymentRequest request, PaymentProvider provider, User user){
        var retVal = new Payment();
        retVal.setServiceProvider(provider.getCode());
        retVal.setStatus(INITIATED);
        retVal.setAmount(request.getAmount());
        retVal.setDescription(request.getDescription());
        retVal.setCreatedBy(user.getPrimaryPhoneNo());
        retVal.setCreatedAt(new Date());
        retVal.setModifiedBy(user.getPrimaryPhoneNo());
        retVal.setModifiedAt(new Date());
        return paymentRepository.save(retVal);
    }

    public Payment update(PaymentUpdateModel paymentUpdateModel) throws InvalidInputException, ServerException {
        var payment = fetchById(paymentUpdateModel.getId());
        ObjectMapperUtils.map(paymentUpdateModel, payment);
        return paymentRepository.save(payment);
    }

    public void validateWebhook(WebhookPaymentModel request)
            throws InvalidInputException, NoSuchAlgorithmException, InvalidKeyException, JsonParseException {
        var service = serviceProvider.getService(true);
        service.validateWebhook(request);
    }

    public String processWebhook(WebhookPaymentModel request) throws JsonParseException, InvalidInputException, ServerException {
        var service = serviceProvider.getService(true);
        service.processWebhook(request);
        return "Webhook processed successfully.";
    }

    public PaymentAdditionalInfo fetchAdditionalInfo(String id) throws InvalidInputException, JsonParseException {
        var payment = fetchById(id);
        return JsonUtils.readObjectFromJson(payment.getAdditionalInfo(), PaymentAdditionalInfo.class);
    }

    public Payment fetchById(String id) throws InvalidInputException {
        var payment = paymentRepository.findById(id);
        if (payment.isEmpty()){
            throw new InvalidInputException("Payment record not found with Id "+id);
        }
        return payment.get();
    }

    public PaymentResponse fetchStatus(PaymentStatusRequest request) throws InvalidInputException, JsonParseException, ServerException {
        var payment = fetchById(request.getPaymentId());
        var isOnline = !(Objects.equals(PaymentProvider.MANUAL.getCode(), payment.getServiceProvider()));
        if (isOnline){
            if (request.getExternal()){
                payment = syncPaymentStatus(isOnline, request);
            }
            return new PaymentResponse(payment, JsonUtils.readObjectFromJson(payment.getAdditionalInfo(), PaymentAdditionalInfo.class));
        }
        throw new UnsupportedOperationException("Manual payments status are not captured.");
    }

    private Payment syncPaymentStatus(Boolean isOnline, PaymentStatusRequest request) throws InvalidInputException, ServerException, JsonParseException {
        var service = serviceProvider.getService(isOnline);
        return service.fetchPaymentStatus(request.getPaymentId());
    }

    public Payment fetchByExternalId(String paymentId) throws InvalidInputException {
        return paymentRepository.findByExternalId(paymentId)
                .orElseThrow(()->new InvalidInputException("Invalid Payment id "+paymentId));
    }
}
