package com.neonlab.common.services;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.entities.Payment;
import com.neonlab.common.enums.PaymentProvider;
import com.neonlab.common.models.PaymentRequest;
import com.neonlab.common.models.PaymentResponse;
import com.neonlab.common.models.razorpay.WebhookPaymentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Loggable
public class ManualPaymentServiceProvider extends AbstractPaymentService{

    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.MANUAL;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public void validateWebhook(WebhookPaymentModel request) {
        throw new UnsupportedOperationException("Webhook is not applicable for manual payments.");
    }

    @Override
    public void processWebhook(WebhookPaymentModel request) {}

    @Override
    public Payment fetchPaymentStatus(String id) {
        return null;
    }
}
