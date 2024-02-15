package org.vaadin.example.views.register.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDto {

    @NotBlank
    private String username;

    @Size(min = 8, max = 64, message = "Password must be 8-64 char long")
    private String password;
}