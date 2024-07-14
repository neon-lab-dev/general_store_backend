package com.neonlab.product.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable
@RestController
public class AuthorizationRoute {

    @GetMapping("/")
    public ApiOutput<?> authorizationRoute(){
        return new ApiOutput<>(HttpStatus.OK.value(), null,"You have reached product service now.");
    }

}
