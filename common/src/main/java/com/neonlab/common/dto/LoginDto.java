package com.neonlab.common.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LoginDto {
    @NotEmpty(message = "UserName should not be empty")
    private String userName;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
