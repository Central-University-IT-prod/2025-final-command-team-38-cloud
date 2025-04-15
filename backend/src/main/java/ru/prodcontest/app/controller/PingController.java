package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "0. PingController: Проверка работоспособности сервиса")
@CrossOrigin
public class PingController {
    @GetMapping("/api/ping")
    @Operation(summary = "Проверка работоспособности сервиса", description = "Возвращает 'pong' в случае успешного ответа от сервера")
    @ApiResponse(responseCode = "200", description = "Сервис работает корректно")
    @ApiResponse(responseCode = "500", description = "Сервис недоступен")
    public String ping() {
        return "pong";
    }
}