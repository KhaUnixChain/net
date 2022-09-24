package com.fastshop.net.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Changed {
    @NotBlank(message = "(*) Vui lòng nhập mật khẩu hiện tại")
    private String current;

    @NotBlank(message = "(*) Password can't blank")
    private String password1;

    @NotBlank(message = "(*) Password can't blank")
    private String password2;
}
