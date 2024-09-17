package com.api.keycloak_admin.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {
    @NotBlank(message = "Type cannot be blank or null")
    private String type;

    @NotBlank(message = "Value cannot be blank or null")
    private String value;

    @NotNull(message = "Temporary field cannot be null")
    private boolean temporary;
}
