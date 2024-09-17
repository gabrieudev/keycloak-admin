package com.api.keycloak_admin.rest.controller;

import com.api.keycloak_admin.service.RoleService;
import com.api.keycloak_admin.rest.dto.CreateRoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/roles")
@SecurityRequirement(name = "Keycloak")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(
            summary = "Create role",
            description = "Endpoint that creates a role",
            tags = "Role"
    )
    @PostMapping
    public ResponseEntity<String> createRole(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @Valid @RequestBody CreateRoleRequest createRoleRequest
    ) {
        roleService.createRole(token, createRoleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }

    @Operation(
            summary = "Delete role",
            description = "Endpoint that deletes a role",
            tags = "Role"
    )
    @DeleteMapping("/{roleName}")
    public ResponseEntity<String> deleteRole(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @PathVariable("roleName") String roleName
    ) {
        roleService.deleteRole(token, roleName);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted successfully");
    }

}

