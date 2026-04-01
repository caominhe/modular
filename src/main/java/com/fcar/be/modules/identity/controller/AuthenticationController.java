package com.fcar.be.modules.identity.controller;

import com.fcar.be.core.common.dto.ApiResponse;
import com.fcar.be.modules.identity.dto.request.AuthenticationRequest;
import com.fcar.be.modules.identity.dto.response.AuthenticationResponse;
import com.fcar.be.modules.identity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}