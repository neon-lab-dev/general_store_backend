package com.neonlab.loginservice.controller;

import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.dto.AddressDto;
import com.neonlab.common.dto.ApiOutput;
import com.neonlab.common.dto.UserDto;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.searchcriteria.AddressSearchCriteria;
import com.neonlab.common.searchcriteria.UserSearchCriteria;
import com.neonlab.common.services.UserService;
import com.neonlab.loginservice.apis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Loggable
@RequestMapping("/v1/profile")
public class ProfileController {
    @Autowired
    private AddAddressApi addAddressApi;

    @Autowired
    private DeleteAddressApi deleteAddressApi;

    @Autowired
    private UpdateAddressApi updateAddressApi;

    @Autowired
    private FetchAddressApi fetchAddressApi;

    @Autowired
    private UserService userService;

    @Autowired
    private FetchUserApi fetchUserApi;

    @Autowired
    private UpdateUserApi updateUserApi;

    @Autowired
    private FetchUsersByAdminApi fetchUsersByAdminApi;

    @PostMapping("/address/add")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiOutput<AddressDto> add(@RequestBody AddressDto addressDto){
        return addAddressApi.addAddress(addressDto);
    }

    @DeleteMapping("/address/delete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiOutput<?> delete(@RequestBody List<String> ids){
        return deleteAddressApi.delete(ids);
    }

    @PostMapping("/address/update")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public  ApiOutput<AddressDto> update(@RequestBody AddressDto addressDto){
        return updateAddressApi.update(addressDto);
    }

    @GetMapping("/address/fetch")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public  ApiOutput<?> fetch(final AddressSearchCriteria searchCriteria) throws InvalidInputException {
        return fetchAddressApi.fetch(searchCriteria);

    }
    @GetMapping("/address/admin/fetch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public  ApiOutput<?> fetchAll(final AddressSearchCriteria searchCriteria) throws InvalidInputException {
        searchCriteria.setAdmin(true);
        return fetchAddressApi.fetch(searchCriteria);
    }

    @GetMapping("/user/fetch")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiOutput<?> fetch(){
        return fetchUserApi.fetch();
    }

    @PutMapping("/user/update")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiOutput<?> update(@RequestBody UserDto userDto){
        return updateUserApi.update(userDto);
    }

    @GetMapping("/admin/fetch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiOutput<?> fetch(final UserSearchCriteria searchCriteria){
        return fetchUsersByAdminApi.fetch(searchCriteria);
    }

}
