package com.api.keycloak_admin.rest.dto;

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
public class ChangeRoleRequest {
    @NotBlank(message = "Username cannot be blank or null")
    private String username;

    @NotNull(message = "List of roles cannot be null")
    private List<RoleDTO> roles;
}
