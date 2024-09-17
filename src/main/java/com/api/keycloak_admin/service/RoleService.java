package com.api.keycloak_admin.service;

import com.api.keycloak_admin.client.KeycloakAdminClient;
import com.api.keycloak_admin.exception.ResourceNotFoundException;
import com.api.keycloak_admin.rest.dto.CreateRoleRequest;
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
public class RoleService {

    private final KeycloakAdminClient keycloakAdminClient;

    @Value("${keycloak.back-end-client-id}")
    private String clientId;

    @Autowired
    public RoleService(KeycloakAdminClient keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }

    public UUID getRoleUuid(String token, String roleName) {
        UUID clientUuid = getClientUuid(token);
        ResponseEntity<IdResponse> response = keycloakAdminClient.getRoleUuid(token, clientUuid, roleName);
        if (response.getStatusCode().value() != 200) {
            log.error("Error while getting role UUID at {}", Instant.now());
        }
        return Objects.requireNonNull(response.getBody()).getId();
    }

    public UUID getClientUuid(String token) {
        ResponseEntity<List<IdResponse>> response = keycloakAdminClient.getClientUuid(token, clientId);
        if (response.getStatusCode().value() != 200) {
            log.error("Error while getting client UUID at {}", Instant.now());
        }
        if (Objects.requireNonNull(response.getBody()).isEmpty()) {
            throw new ResourceNotFoundException("Client not found");
        }
        return Objects.requireNonNull(response.getBody()).get(0).getId();
    }

    public void createRole(
            String token,
            CreateRoleRequest createRoleRequest
    ) {
        UUID clientUuid = getClientUuid(token);
        ResponseEntity<Void> response = keycloakAdminClient.createRole(token, clientUuid, createRoleRequest);
        if (response.getStatusCode().value() != 200) {
            log.error("Error while creating role at {}", Instant.now());
        }
    }

    public void deleteRole(String token, String roleName) {
        UUID clientUuid = getClientUuid(token);
        ResponseEntity<Void> response = keycloakAdminClient.deleteRole(token, clientUuid, roleName);
        if (response.getStatusCode().value() != 204) {
            log.error("Error while deleting role at {}", Instant.now());
        }
    }

}
