package com.api.keycloak_admin.rest.controller;

import com.api.keycloak_admin.rest.dto.ChangeRoleRequest;
import com.api.keycloak_admin.rest.dto.CreateUserRequest;
import com.api.keycloak_admin.service.UserService;
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
@RequestMapping("/users")
@SecurityRequirement(name = "Keycloak")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create user",
            description = "Endpoint that creates a user",
            tags = "User"
    )
    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @Valid @RequestBody CreateUserRequest createUserRequest
    ) {
        userService.createUser(token, createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @Operation(
            summary = "Delete user",
            description = "Endpoint that deletes a user",
            tags = "User"
    )
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @PathVariable("username") String username
    ) {
        userService.deleteUser(token, username);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @Operation(
            summary = "Assign role",
            description = "Endpoint that assigns role to user",
            tags = "User"
    )
    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @Valid @RequestBody ChangeRoleRequest changeRoleRequest
    ) {
        userService.assignRole(token, changeRoleRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Role assigned successfully");
    }

    @Operation(
            summary = "Unassign role",
            description = "Endpoint that unassigns user's role",
            tags = "User"
    )
    @DeleteMapping("/unassign")
    public ResponseEntity<String> unassignRole(
            @RequestHeader(value = "Authorization", required = false)
            @Parameter(hidden = true)
            String token,
            @Valid @RequestBody ChangeRoleRequest changeRoleRequest
    ) {
        userService.unassignRole(token, changeRoleRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Role unassigned successfully");
    }


}
