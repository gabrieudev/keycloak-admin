package com.api.keycloak_admin.service;

import com.api.keycloak_admin.client.KeycloakAdminClient;
import com.api.keycloak_admin.exception.ResourceNotFoundException;
import com.api.keycloak_admin.rest.dto.ChangeRoleRequest;
import com.api.keycloak_admin.rest.dto.CreateUserRequest;
import com.api.keycloak_admin.rest.dto.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final KeycloakAdminClient keycloakAdminClient;
    private final RoleService roleService;

    @Value("${keycloak.back-end-client-id}")
    private String clientId;

    @Autowired
    public UserService(KeycloakAdminClient keycloakAdminClient, RoleService roleService) {
        this.keycloakAdminClient = keycloakAdminClient;
        this.roleService = roleService;
    }

    public UUID getUserUuid(String token, String username) {
        ResponseEntity<List<IdResponse>> response = keycloakAdminClient.getUserUuid(token, username);
        if (response.getStatusCode().value() != 200) {
            log.error("Error while getting user UUID at {}", Instant.now());
        }
        if (Objects.requireNonNull(response.getBody()).isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return Objects.requireNonNull(response.getBody()).get(0).getId();
    }

    public void createUser(String token, CreateUserRequest createUserRequest) {
        ResponseEntity<Void> response = keycloakAdminClient.createUser(token, createUserRequest);
        if (response.getStatusCode().value() != 200) {
            log.error("Error while creating user at {}", Instant.now());
        }
    }

    public void deleteUser(String token, String username) {
        UUID userUuid = getUserUuid(token, username);
        ResponseEntity<Void> response = keycloakAdminClient.deleteUser(token, userUuid);
        if (response.getStatusCode().value() != 204) {
            log.error("Error while deleting user at {}", Instant.now());
        }
    }

    public void assignRole(String token, ChangeRoleRequest changeRoleRequest) {
        UUID clientUuid = roleService.getClientUuid(token);
        UUID userUuid = getUserUuid(token, changeRoleRequest.getUsername());

        changeRoleRequest.getRoles()
                .forEach(roleDTO -> {
                    UUID roleUuid = roleService.getRoleUuid(token, roleDTO.getName());
                    roleDTO.setId(roleUuid);
                });

        ResponseEntity<Void> response = keycloakAdminClient.assignRole(token, userUuid, clientUuid, changeRoleRequest.getRoles());
        if (response.getStatusCode().value() != 204) {
            log.error("Error while assigning role to user at {}", Instant.now());
        }
    }

    public void unassignRole(String token, ChangeRoleRequest changeRoleRequest) {
        UUID clientUuid = roleService.getClientUuid(token);
        UUID userUuid = getUserUuid(token, changeRoleRequest.getUsername());

        changeRoleRequest.getRoles()
                .forEach(roleDTO -> {
                    UUID roleUuid = roleService.getRoleUuid(token, roleDTO.getName());
                    roleDTO.setId(roleUuid);
                });

        ResponseEntity<Void> response = keycloakAdminClient.unassignRole(token, userUuid, clientUuid, changeRoleRequest.getRoles());
        if (response.getStatusCode().value() != 204) {
            log.error("Error while unassigning role to user at {}", Instant.now());
        }
    }

}
