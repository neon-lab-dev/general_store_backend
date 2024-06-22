package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.models.UserReportModel;
import com.neonlab.common.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Loggable
@RequiredArgsConstructor
public class UserReportApi {

    private final UserService userService;

    public ApiOutput<UserReportModel> process(){
        return new ApiOutput<>(HttpStatus.OK.value(), null, userService.getReport());
    }

}
