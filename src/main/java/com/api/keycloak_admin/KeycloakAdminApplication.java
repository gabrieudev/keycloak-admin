package com.api.keycloak_admin;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SecurityScheme(
		name = "Keycloak"
		, openIdConnectUrl = "${keycloak.base-url}"+"/realms/"+"${keycloak.realm}"+"/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
@EnableFeignClients
public class KeycloakAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAdminApplication.class, args);
	}

}
