package com.nixon.cinema.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                description = "Cinema management api, that allows: <br> " +
                        "<ul>" +
                        "<li>Movie exhibition management</li>" +
                        "<li>Capacity management</li>" +
                        "<li>tickets purchases, etc<l/i>" +
                        "</ul> ",
                title = "Cinema Management",
                version = "V0.9.9",
                contact = @Contact(
                        name = "Nixon Sadique",
                        email = "nixonsadique2005@gmail.com"
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "Bearer Authentication"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Insert a valid jwt token. You can get yours from the path: /auth/",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfiguration {
}
