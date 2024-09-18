package com.api.keycloak_admin.client;

import com.api.keycloak_admin.rest.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "KeycloakAdminClient",
        url = "${keycloak.base-url}"+"/admin/realms/"+"${keycloak.realm}"
)
public interface KeycloakAdminClient {

    @GetMapping("/users")
    ResponseEntity<List<IdResponse>> getUserUuid(
            @RequestHeader("Authorization") String token,
            @RequestParam("username") String username
    );

    @GetMapping("/clients/{client-uuid}/roles/{role-name}")
    ResponseEntity<IdResponse> getRoleUuid(
            @RequestHeader("Authorization") String token,
            @PathVariable("client-uuid") UUID clientUuid,
            @PathVariable("role-name") String roleName
    );

    @GetMapping("/clients")
    ResponseEntity<List<IdResponse>> getClientUuid(
            @RequestHeader("Authorization") String token,
            @RequestParam("clientId") String clientId
    );

    @PostMapping("/clients/{client-uuid}/roles")
    ResponseEntity<Void> createRole(
            @RequestHeader("Authorization") String token,
            @PathVariable("client-uuid") UUID clientUuid,
            @RequestBody CreateRoleRequest createRoleRequest
    );

    @DeleteMapping("/clients/{client-uuid}/roles/{role-name}")
    ResponseEntity<Void> deleteRole(
            @RequestHeader("Authorization") String token,
            @PathVariable("client-uuid") UUID clientUuid,
            @PathVariable("role-name") String roleName
    );

    @PostMapping("/users")
    ResponseEntity<Void> createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateUserRequest createUserRequest
    );

    @DeleteMapping("/users/{user-uuid}")
    ResponseEntity<Void> deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("user-uuid") UUID userUuid
    );

    @PostMapping("/users/{user-uuid}/role-mappings/clients/{client-uuid}")
    ResponseEntity<Void> assignRole(
            @RequestHeader("Authorization") String token,
            @PathVariable("user-uuid") UUID userId,
            @PathVariable("client-uuid") UUID clientUuid,
            @RequestBody List<RoleDTO> roles
    );

    @DeleteMapping("/users/{user-uuid}/role-mappings/clients/{client-uuid}")
    ResponseEntity<Void> unassignRole(
            @RequestHeader("Authorization") String token,
            @PathVariable("user-uuid") UUID userId,
            @PathVariable("client-uuid") UUID clientUuid,
            @RequestBody List<RoleDTO> roles
    );

    @GetMapping("/users")
    ResponseEntity<List<UserResponse>> getUsers(
            @RequestHeader("Authorization") String token,
            @RequestParam("first") int page,
            @RequestParam("max") int size
    );

}

