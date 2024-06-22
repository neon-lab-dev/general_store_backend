package com.neonlab.common.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;



@Data
public class EmailSendDto {
    @NotEmpty(message = "to cannot be empty or null")
    private String to;
    @NotEmpty(message = "cc cannot be empty or null")
    private String cc;
    @NotEmpty(message = "subject cannot be empty or null")
    private String subject;
    @NotEmpty(message = "body cannot be empty or null")
    private String body;
}
