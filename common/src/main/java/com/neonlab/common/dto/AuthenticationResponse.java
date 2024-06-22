package com.neonlab.common.dto;

import com.neonlab.common.entities.Otp;
import com.neonlab.common.enums.VerificationPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String status;

}
