package com.neonlab.common.dto;
import com.neonlab.common.utilities.StringUtil;
import com.neonlab.common.validationGroups.AddOrderValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Objects;

@Data
public class AddressDto {

    @NotEmpty(groups = AddOrderValidationGroup.class, message = "Address Id is mandatory")
    private String id;

    private String landmark;

    @NotBlank(message = "Address line 1 cannot be empty")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotBlank(message = "Pin code cannot be empty")
    private String pincode;

    @NotBlank(message = "Address Name cannot be blank")
    private String addressName;

    private boolean primaryAddress;

    public boolean isAddressLine2Equal(String addressLine2){
        if(addressLine2==null || this.addressLine2.equalsIgnoreCase(addressLine2)){
            return true;
        }
        return false;
    }



}

