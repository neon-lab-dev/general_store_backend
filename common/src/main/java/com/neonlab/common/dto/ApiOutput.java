package com.neonlab.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiOutput<T> {

    private int statusCode;
    private String message;
    private String errorMessage;
    private T responseBody;

    public ApiOutput(int statusCode, String errorMessage){
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public ApiOutput(int statusCode, String message, T responseBody){
        this.statusCode = statusCode;
        this.message = message;
        this.responseBody = responseBody;
    }

}
