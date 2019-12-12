package com.vandalar.server.user.web.resource;

import com.vandalar.server.user.RegistrationService;
import com.vandalar.server.user.web.RegistrationRequest;
import com.vandalar.server.user.web.RegistrationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationResource {

    private final RegistrationService registrationService;

    public RegistrationResource(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/api/v1/auth/register")
    public RegistrationResponse register(@Validated @RequestBody RegistrationRequest request) {
        String publicId = registrationService.register(request.getPrivateId(), request.getName());

        return new RegistrationResponse(publicId);
    }
}