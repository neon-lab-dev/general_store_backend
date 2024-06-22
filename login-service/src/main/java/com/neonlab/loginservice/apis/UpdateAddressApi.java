package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AddressDto;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.AddressService;
import com.neonlab.common.utilities.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Service
@Loggable
public class UpdateAddressApi {

    private static final String UPDATE_MSG="The address has been updated successfully";

    @Autowired
    private AddressService addressService;

    public ApiOutput<AddressDto> update(AddressDto addressDto){
        try{
            validate(addressDto);
            var response = addressService.update(addressDto);
            return new ApiOutput<>(HttpStatus.OK.value(),UPDATE_MSG,response );
        } catch (Exception e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    private void validate(AddressDto addressDto)throws InvalidInputException{
        if(Objects.isNull(addressDto)){
            throw new InvalidInputException("Please provide something to update.");
        }
        if(Strings.isEmpty(addressDto.getId())){
            throw new InvalidInputException(("You need to provide id to update the address."));
        }
    }

}
