package com.neonlab.common.dto;


import lombok.Data;

@Data
public class AuditDto {

    private String rawRequest;
    private String rawResponse;
    private String serviceProvider;


}
