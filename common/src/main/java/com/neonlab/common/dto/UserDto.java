package com.neonlab.common.dto;


import com.neonlab.common.entities.User;
import com.neonlab.common.validationGroups.AddOrderValidationGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
public class UserDto {

    @NotEmpty(groups = AddOrderValidationGroup.class, message = "User Id is mandatory.")
    private String id;
    private String name;
    private String email;
    private String primaryPhoneNo;
    private String secondaryPhoneNo;


    public UserDto(String id){
        this.id = id;
    }

    public static UserDto parse(User user){
        var mapper = new ModelMapper();
        return  mapper.map(user, UserDto.class);
    }

}
