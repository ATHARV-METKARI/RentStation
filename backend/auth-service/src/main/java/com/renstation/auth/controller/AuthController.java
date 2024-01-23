package com.renstation.auth.controller;

import com.renstation.auth.dto.*;
import com.renstation.auth.service.AuthService;
import com.renstation.common.dto.StandardApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public ResponseEntity<StandardApiResponse<Void>> sendOtp(@Valid @RequestBody SendOtpRequest request) {
        authService.sendOtp(request);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder()
                .success(true)
                .message("OTP sent successfully")
                .build());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<StandardApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        AuthResponse response = authService.verifyOtp(request);
        return ResponseEntity.ok(StandardApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Authenticated successfully")
                .data(response)
                .build());
    }
}
