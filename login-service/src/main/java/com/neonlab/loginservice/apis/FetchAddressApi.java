package com.neonlab.loginservice.apis;

import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.searchcriteria.AddressSearchCriteria;
import com.neonlab.common.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FetchAddressApi {
    @Autowired
    private AddressService addressService;

    public ApiOutput<?> fetch(final AddressSearchCriteria searchCriteria) throws InvalidInputException {
        return new ApiOutput<>(HttpStatus.OK.value(), null,addressService.fetch(searchCriteria));
    }
}
