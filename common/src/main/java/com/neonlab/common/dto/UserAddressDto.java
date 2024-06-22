package com.neonlab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserAddressDto {

    private UserDto userDetails;
    private List<AddressDto> addressDetailsList;

}
