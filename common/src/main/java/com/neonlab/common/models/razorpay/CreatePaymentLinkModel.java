package com.neonlab.common.models.razorpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neonlab.common.entities.User;
import com.neonlab.common.models.PaymentRequest;
import com.neonlab.common.utilities.DateUtils;
import com.neonlab.common.utilities.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePaymentLinkModel {

    private BigDecimal amount;
    private String currency;
    @JsonProperty(value = "accept_partial")
    private boolean acceptPartial;
    @JsonProperty("reference_id")
    private String referenceId;
    private String description;
    private Customer customer;
    @JsonProperty("reminder_enable")
    private boolean reminderEnable;
    @JsonProperty("expire_by")
    private Integer expireBy;
    @JsonProperty("upi_link")
    private Boolean upiLink;
    @JsonProperty("short_url")
    private String shortUrl;
    private Notify notify;
    private String status;
    @JsonProperty("id")
    private String paymentLinkId;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Customer {
        private String name;
        private String contact;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Notify {
        private boolean sms;
        private boolean email;
    }

    @Builder(builderMethodName = "paymentLinkRequestBuilder")
    public CreatePaymentLinkModel(
            final PaymentRequest request,
            final String referenceId,
            final Integer validity,
            final User user)
    {
        this.amount = request.getAmount().multiply(BigDecimal.valueOf(100));
        this.currency = "INR";
        this.acceptPartial = false;
        this.referenceId = referenceId;
        this.description = request.getDescription();
        this.customer = new Customer(user.getName(), String.format("+91%s",user.getPrimaryPhoneNo()), user.getEmail());
        this.reminderEnable = false;
        this.expireBy = (int) DateUtils.getUnixTime(DateUtils.getDateAfterNMinutes(validity));
        this.notify = new Notify(false, false);
        this.upiLink = request.getUpiLink();
    }

}


