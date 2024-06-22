package com.neonlab.loginservice.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.loginservice.apis.UserReportApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable
@RequestMapping(value = "/v1/report")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final UserReportApi userReportApi;

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiOutput<?> userReport(){
        return userReportApi.process();
    }

}
