package com.neonlab.common.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String sender;
    private String phone;
    private String message;
    private String otp;
    private String purpose;
}
