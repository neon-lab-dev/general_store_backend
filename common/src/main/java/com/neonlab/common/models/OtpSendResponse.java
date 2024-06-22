package com.neonlab.common.models;

import com.neonlab.common.enums.AuthStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtpSendResponse {

    private String message;
    private AuthStatus status;

}
