package com.neonlab.common.dto;
import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String primaryPhoneNo;
    private String SecondaryPhoneNo;

}
