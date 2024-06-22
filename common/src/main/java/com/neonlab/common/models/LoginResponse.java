package com.neonlab.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neonlab.common.enums.AuthStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private String token;
    private boolean isExisting;
    private AuthStatus status;

    public LoginResponse(AuthStatus status, boolean isExisting){
        this.status = status;
        this.isExisting = isExisting;
    }

}
