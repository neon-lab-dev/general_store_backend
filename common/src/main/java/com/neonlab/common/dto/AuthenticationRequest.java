package com.neonlab.common.dto;

import lombok.Data;


/**
 * Authentication Request
 */

@Data
public class AuthenticationRequest {
    private String phone;
    private String authCode;
}
