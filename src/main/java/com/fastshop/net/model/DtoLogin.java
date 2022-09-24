package com.fastshop.net.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoLogin {
    @NotBlank(message = "(*) Username or email can't blank")
    private String usernameOrEmail;

    @NotBlank(message = "(*) Password can't blank")
    private String password;
}
