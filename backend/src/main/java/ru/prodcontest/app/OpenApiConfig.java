package ru.prodcontest.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "MentorFinder",
                description = "API сервиса поиска ментора в программировании",
                version = "1.0.0",
                contact = @Contact(
                        name = "PRODусы"
                )
        )
)
public final class OpenApiConfig {
    private OpenApiConfig() {}
}