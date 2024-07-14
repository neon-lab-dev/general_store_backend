package com.neonlab.loginservice.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Loggable
public class ReachOutController {

    private static final String REACH_OUT_MESSAGE = "You have successfully reached login service.";

    @GetMapping("/")
    public ApiOutput<?> slashReachOut(){
        return new ApiOutput<>(HttpStatus.OK.value(), null, REACH_OUT_MESSAGE);
    }

    @GetMapping("/login/")
    public ApiOutput<?> loginSlashReachOut(){
        return new ApiOutput<>(HttpStatus.OK.value(), null, REACH_OUT_MESSAGE);
    }

}
