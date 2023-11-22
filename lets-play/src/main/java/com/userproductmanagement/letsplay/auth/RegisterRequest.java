package com.userproductmanagement.letsplay.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 20, message = "Name needs to be between 2-20 characters")
    private String name;
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email needs to be valid")
    private String email;
    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be blank")
    private String password;
}
