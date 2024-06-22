package com.neonlab.common.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.config.RazorPayProperties;
import com.neonlab.common.entities.Payment;
import com.neonlab.common.entities.User;
import com.neonlab.common.enums.PaymentProvider;
import com.neonlab.common.enums.PaymentStatus;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.*;
import com.neonlab.common.models.razorpay.CreatePaymentLinkModel;
import com.neonlab.common.models.razorpay.WebhookBody;
import com.neonlab.common.models.razorpay.WebhookPaymentModel;
import com.neonlab.common.utilities.EncryptionUtils;
import com.neonlab.common.utilities.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import static com.neonlab.common.constants.RazorPayConstants.*;

@Slf4j
@Service
@Loggable
public class OnlinePaymentService extends AbstractPaymentService {

    @Autowired private RazorPayProperties properties;
    @Autowired private WebfluxRestRequestService restService;

    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.RAZORPAY;
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) throws InvalidInputException, ServerException, JsonParseException {
        validatePaymentRequest(request);
        var user = userService.getLoggedInUser();
        var paymentRecord = paymentRecordService.initiate(request, getProvider(), user);
        var requestBody = getPaymentRequest(request, paymentRecord, user);
        var response = restService.post(RestRequestModel.buildRestRequest()
                .url(properties.getPaymentLinkBaseUrl())
                .method(HttpMethod.POST)
                .headers(getRequestHeader())
                .requestBody(requestBody)
                .requestClazz(CreatePaymentLinkModel.class)
                .build());
        var responseObj = JsonUtils.readObjectFromJson(response, CreatePaymentLinkModel.class);
        var additionalInfo = PaymentAdditionalInfo.buildPaymentAdditionalInfo()
                .shortUrl(responseObj.getShortUrl())
                .paymentLinkId(responseObj.getPaymentLinkId())
                .build();
        paymentRecordService.update(PaymentUpdateModel.buildPaymentUpdateModel()
                .id(paymentRecord.getId())
                .additionalInfo(JsonUtils.jsonOf(additionalInfo))
                .build()
        );
        return new PaymentResponse(paymentRecord, additionalInfo);
    }

    private CreatePaymentLinkModel getPaymentRequest(PaymentRequest request, Payment paymentRecord, User user) throws InvalidInputException {
        return CreatePaymentLinkModel.paymentLinkRequestBuilder()
                .request(request)
                .referenceId(paymentRecord.getId())
                .validity(getExpireAfter())
                .user(user)
                .build();
    }

    private HttpHeaders getRequestHeader(){
        var retVal = new HttpHeaders();
        retVal.setContentType(MediaType.APPLICATION_JSON);
        retVal.setBasicAuth(EncryptionUtils.getBase64Token(properties.getApiKeyId(), properties.getApiKeySecret()));
        return retVal;
    }

    @Override
    public void validateWebhook(WebhookPaymentModel request)
            throws NoSuchAlgorithmException, InvalidKeyException, InvalidInputException, JsonParseException {
        /*var compiledSignature = EncryptionUtils.getHmacSignature(properties.getWebhookSecret(), request.getBody(), EncryptionUtils.HMAC_SHA256);
        if (!Objects.equals(compiledSignature, request.getSignature())){
            throw new InvalidInputException("Unauthorized access.");
        }*/
        var paymentLink = JsonUtils.readObjectFromJson(request.getBody(), WebhookBody.class);
        if (!paymentLink.getContains().contains(PAYMENT_LINK)){
            throw new InvalidInputException("Payment link entity is not available in the webhook response.");
        }
        var paymentLinkEntity = paymentLink.getPayload().getPaymentLink().get(ENTITY);
        var additionalInfo = paymentRecordService.fetchAdditionalInfo(paymentLinkEntity.getReferenceId());
        if (Objects.equals(request.getEventId(), additionalInfo.getEntityId())){
            throw new InvalidInputException("Payment link has already been processed.");
        }
    }

    @Override
    public void processWebhook(WebhookPaymentModel request) throws JsonParseException, InvalidInputException, ServerException {
        var body = JsonUtils.readObjectFromJson(request.getBody(), WebhookBody.class);
        var paymentLinkEntity = body.getPayload().getPaymentLink().get(ENTITY);
        var status = getPaymentStatus(paymentLinkEntity);
        var additionalInfo = paymentRecordService.fetchAdditionalInfo(paymentLinkEntity.getReferenceId());
        additionalInfo.setEntityId(request.getEventId());
        paymentRecordService.update(PaymentUpdateModel.buildPaymentUpdateModel()
                        .id(paymentLinkEntity.getReferenceId())
                        .paymentStatus(status)
                        .additionalInfo(JsonUtils.jsonOf(additionalInfo))
                        .externalId(additionalInfo.getPaymentLinkId())
                .build()
        );
    }

    private PaymentStatus getPaymentStatus(CreatePaymentLinkModel paymentLinkEntity) {
       return switch (paymentLinkEntity.getStatus()){
           case PAID -> PaymentStatus.SUCCESS;
           case CREATED -> PaymentStatus.INITIATED;
           default -> PaymentStatus.FAILED;
       };
    }


    @Override
    public Payment fetchPaymentStatus(String id) throws InvalidInputException, JsonParseException, ServerException {
        var payment = paymentRecordService.fetchById(id);
        var additionalInfo = JsonUtils.readObjectFromJson(payment.getAdditionalInfo(), PaymentAdditionalInfo.class);
        var url = String.format("%s/%s", properties.getPaymentLinkBaseUrl(), additionalInfo.getPaymentLinkId());
        var response = restService.get(RestRequestModel.buildRestRequest()
                        .url(url)
                        .method(HttpMethod.GET)
                        .headers(getRequestHeader())
                .build());
        var responseObj = JsonUtils.readObjectFromJson(response, CreatePaymentLinkModel.class);
        var status = getPaymentStatus(responseObj);
        return paymentRecordService.update(PaymentUpdateModel.buildPaymentUpdateModel()
                        .id(payment.getId())
                        .paymentStatus(status)
                        .externalId(additionalInfo.getPaymentLinkId())
                .build());
    }
}
