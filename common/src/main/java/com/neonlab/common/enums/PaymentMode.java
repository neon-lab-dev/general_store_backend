package com.neonlab.common.enums;
import com.neonlab.common.expectations.InvalidInputException;
import lombok.Getter;


@Getter
public enum PaymentMode {
    CASH_ON_DELIVERY("Cash On Delivery"),
    ONLINE_PAYMENT("Online Payment"),
    PICKUP_AT_SHOP("Pickup at shop");

    public final String paymentMode;

    PaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public static PaymentMode fromString(String inputStatus) throws InvalidInputException {
        for (PaymentMode paymentMode1 : PaymentMode.values()) {
            if (paymentMode1.paymentMode.equalsIgnoreCase(inputStatus)) {
                return paymentMode1;
            }
        }
        throw new InvalidInputException("Unknown Status: " + inputStatus);
    }
}
