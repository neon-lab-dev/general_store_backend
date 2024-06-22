package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.UserDto;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.StringUtil;
import com.neonlab.common.utilities.ValidationUtils;
import com.neonlab.common.validationGroups.UpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class UpdateUserApi {

    private static final String UPDATE_MSG="User is updated successfully";

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationUtils validationUtils;

    public ApiOutput<?> update(UserDto userDto){
        try{
            validate(userDto);
            UserDto retVal = userService.update(userDto);
            return new ApiOutput<>(HttpStatus.OK.value(), UPDATE_MSG, retVal);
        } catch (Exception e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    private void validate(UserDto userDto) throws InvalidInputException {
        if(!StringUtil.isNullOrEmpty(userDto.getPrimaryPhoneNo()) && userDto.getPrimaryPhoneNo().length() != 10){
            throw new InvalidInputException("Invalid primary phone number.");
        }
        if(!StringUtil.isNullOrEmpty(userDto.getSecondaryPhoneNo()) && userDto.getSecondaryPhoneNo().length() != 10){
            throw new InvalidInputException("Invalid secondary phone number.");
        }
        if (!StringUtil.isNullOrEmpty(userDto.getPrimaryPhoneNo())){
            var existing = userService.existingContactNo(userDto.getPrimaryPhoneNo());
            if (existing){
                throw new InvalidInputException("User exists with the given primary phone no "+userDto.getPrimaryPhoneNo());
            }
        }
        if (!StringUtil.isNullOrEmpty(userDto.getSecondaryPhoneNo())){
            var existing = userService.existingContactNo(userDto.getSecondaryPhoneNo());
            if (existing){
                throw new InvalidInputException("User exists with the given secondary phone no "+userDto.getSecondaryPhoneNo());
            }
        }
        if (!StringUtil.isNullOrEmpty(userDto.getEmail())){
            var existing = userService.existingEmail(userDto.getEmail());
            if (existing){
                throw new InvalidInputException("User exists with the given email "+userDto.getEmail());
            }
        }
    }
}
