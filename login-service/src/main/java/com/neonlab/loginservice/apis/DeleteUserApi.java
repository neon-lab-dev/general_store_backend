package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.services.AddressService;
import com.neonlab.common.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Loggable
@RequiredArgsConstructor
public class DeleteUserApi {

    private static final String MSG = "User deleted successfully.";

    private final UserService userService;
    private final AddressService addressService;

    public ApiOutput<String> delete() throws InvalidInputException, ServerException {
        validate();
        addressService.deleteAll();
        userService.delete();
        return new ApiOutput<>(HttpStatus.OK.value(), null, MSG);
    }

    private void validate() throws InvalidInputException {
        var userMayBe = Optional.ofNullable(userService.getLoggedInUser());
        if (userMayBe.isEmpty()){
            throw new InvalidInputException("User not found in the system.");
        }
    }

}
