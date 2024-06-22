package com.neonlab.common.enums;

import lombok.Getter;

@Getter
public enum PaymentProvider {

    MANUAL(0),
    RAZORPAY(1);

    private final int code;

    PaymentProvider(int code){
        this.code = code;
    }

}
