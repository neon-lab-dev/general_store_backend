package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.UserDto;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Loggable
public class FetchUserApi {

    @Autowired
    private UserService userService;

    public ApiOutput<?> fetch()  {
        try{
            UserDto retVal = userService.fetch();
            return new ApiOutput<>(HttpStatus.OK.value(), null,retVal);

        } catch (Exception e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
