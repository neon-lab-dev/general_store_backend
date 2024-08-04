package com.neonlab.product.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.ReachOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable
@RestController
@RequiredArgsConstructor
public class AuthorizationRoute {

    private final ReachOutService reachOutService;

    @GetMapping("/")
    public String authorizationRoute() throws InvalidInputException {
        return reachOutService.getIndexPage();
    }

}
