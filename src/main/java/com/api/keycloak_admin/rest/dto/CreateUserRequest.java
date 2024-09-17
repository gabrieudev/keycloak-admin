package com.api.keycloak_admin.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Username cannot be blank or null")
    private String username;

    @NotNull(message = "Enabled field cannot be null")
    private boolean enabled;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "First name cannot be blank or null")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank or null")
    private String lastName;

    @NotNull(message = "Credentials field cannot be null")
    private List<CredentialsDTO> credentials;
}
