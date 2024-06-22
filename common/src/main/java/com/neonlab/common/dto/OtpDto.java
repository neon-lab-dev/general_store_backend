package com.neonlab.common.dto;

import lombok.Data;

@Data
public class OtpDto {

    private String communicatedTo;
    private String verificationMode;
    private String otp;
    private String serviceProvider;
    private String status;
    private String expiryTime;
    private String purpose;

}
