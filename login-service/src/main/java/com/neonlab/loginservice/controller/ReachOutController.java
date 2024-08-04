package com.neonlab.loginservice.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.ReachOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable
@RestController
@RequiredArgsConstructor
public class ReachOutController {

    private static final String REACH_OUT_MESSAGE = "You have successfully reached login service.";

    private final ReachOutService reachOutService;

    @GetMapping("/")
    public String slashReachOut() throws InvalidInputException {
        return reachOutService.getIndexPage();
    }

    @GetMapping("/login/")
    public String loginSlashReachOut() throws InvalidInputException {
        return reachOutService.getIndexPage();
    }

}
