package com.example.projetoSpring.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String userName;
    private String password;
    @Email
    private String email;
}
