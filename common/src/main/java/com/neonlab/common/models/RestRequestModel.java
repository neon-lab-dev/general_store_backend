package com.neonlab.common.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Data
public class RestRequestModel {

    private String url;
    private HttpMethod method;
    private MultiValueMap<String, String> params;
    private HttpHeaders headers;
    private Object requestBody;
    private Class<?> requestClazz;
    private MultiValueMap<String, HttpEntity<?>> multipartBody;

    @Builder(builderMethodName = "buildRestRequest")
    public RestRequestModel(
            final String url,
            final HttpMethod method,
            final MultiValueMap<String, String> params,
            final HttpHeaders headers,
            final Object requestBody,
            final Class<?> requestClazz,
            final MultiValueMap<String,HttpEntity<?>> multipartBody){
        this.url = url;
        this.method = method;
        this.params = params;
        this.headers = headers;
        this.requestBody = requestBody;
        this.requestClazz = requestClazz;
        this.multipartBody = multipartBody;
    }


}
