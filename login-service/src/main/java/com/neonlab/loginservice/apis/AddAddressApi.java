package com.neonlab.loginservice.apis;


import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AddressDto;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.entities.User;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.AddressService;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Loggable
public class AddAddressApi {
    private static final String ADD_ADDRESS_MSG="Address added Successfully";

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    public ApiOutput<AddressDto> addAddress(AddressDto addressDto){
        try{
            validate(addressDto);
            AddressDto response = addressService.addAddress(addressDto);
            return new ApiOutput<>(HttpStatus.OK.value(), ADD_ADDRESS_MSG,response);
        } catch (Exception e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    private void validate(AddressDto addressDto) throws InvalidInputException {
        if(Objects.isNull(addressDto)){
            throw new NullPointerException("Address is null or empty please add something");
        }
        User user = userService.getLoggedInUser();
        if(StringUtil.isNullOrEmpty(addressDto.getAddressName())){
            throw new InvalidInputException("Address name is not provided");
        }
        if(StringUtil.isNullOrEmpty(addressDto.getAddressLine1())){
            throw new InvalidInputException("Address Line is not provided");
        }
        if(StringUtil.isNullOrEmpty(addressDto.getCity())){
            throw new InvalidInputException("City is not provided");
        }
        if(StringUtil.isNullOrEmpty(addressDto.getPincode())){
            throw new InvalidInputException("Pincode is not provided");
        }
        if(StringUtil.isNullOrEmpty(addressDto.getState())){
            throw new InvalidInputException("State is not provided");
        }
        if(!addressService.canAddressBeAdded(user)){
            throw new InvalidInputException("You can add only 3 addresses to add new address delete existing address.");
        }
        if(addressService.isSameAddressExist(addressDto,user)){
            throw new InvalidInputException("Address already exist");
        }
    }
}
