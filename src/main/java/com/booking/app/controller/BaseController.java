package com.booking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(BaseController.API_V1)
@RestController
public class BaseController {

    public static final String API_V1 = "/api/v1";

    private final AuthService authService;

    @PostMapping(AuthController.API_NAME + "/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto login) {
        authService.login(login);
        return ResponseEntity.ok().build();
    }

}
