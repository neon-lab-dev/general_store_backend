package com.neonlab.loginservice.apis;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.AddressService;
import com.neonlab.loginservice.controller.ProfileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
@Loggable
public class DeleteAddressApi {

    @Autowired
    private AddressService addressService;

    public ApiOutput<?> delete(List<String> ids){
        try{
            validate(ids);
            String msg = addressService.delete(ids);
            return new ApiOutput<>(HttpStatus.OK.value(), msg,null);

        } catch (Exception e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    private void validate(List<String> ids) throws InvalidInputException{
        if(ids.isEmpty()){
            throw new InvalidInputException("Please provide ids to delete");
        }
    }
}
