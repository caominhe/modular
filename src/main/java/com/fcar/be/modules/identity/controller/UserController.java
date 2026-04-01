package com.fcar.be.modules.identity.controller;

import com.fcar.be.core.common.dto.ApiResponse;
import com.fcar.be.modules.identity.dto.request.UserCreationRequest;
import com.fcar.be.modules.identity.dto.response.UserResponse;
import com.fcar.be.modules.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register") // Cho phép ai cũng được gọi để tạo tài khoản
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }
}