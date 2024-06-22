package com.neonlab.loginservice.apis;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.LoginDto;
import com.neonlab.common.models.LoginResponse;
import com.neonlab.common.services.AuthUserService;
import com.neonlab.common.utilities.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class LoginApi {
    @Autowired
    AuthUserService authUserService;
    @Autowired
    ValidationUtils validationUtils;

    public ApiOutput<LoginResponse> process(LoginDto request) {
        try {
            validationUtils.validate(request);
            return new ApiOutput<>(HttpStatus.OK.value(), "User Login Successful",authUserService.login(request));
        }catch (Exception e){
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
