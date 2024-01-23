package com.renstation.auth.service;

import com.renstation.auth.dto.*;
import com.renstation.auth.entity.*;
import com.renstation.auth.repository.*;
import com.renstation.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OTPService otpService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DeviceSessionRepository deviceSessionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationAuditRepository auditRepository;
    private final PasswordEncoder passwordEncoder;

    public void sendOtp(SendOtpRequest request) {
        otpService.generateAndSendOTP(request.getMobileNumber());
        logAudit(request.getMobileNumber(), "OTP_SENT", "SUCCESS");
    }

    @Transactional
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        try {
            otpService.verifyOTP(request.getMobileNumber(), request.getOtp());
        } catch (Exception e) {
            logAudit(request.getMobileNumber(), "OTP_VERIFY", "FAILED");
            throw e;
        }

        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElseGet(() -> createNewUser(request.getMobileNumber()));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UnauthorizedException("User account is not active");
        }

        DeviceSession session = deviceSessionRepository.findByDeviceIdAndUser_Id(request.getDeviceId(), user.getId())
                .orElseGet(() -> createDeviceSession(user, request.getDeviceId()));
        
        session.setLastActiveAt(LocalDateTime.now());
        deviceSessionRepository.save(session);

        String accessToken = jwtService.generateAccessToken(user, request.getDeviceId());
        String rawRefreshToken = jwtService.generateRefreshToken();

        saveRefreshToken(user, session, rawRefreshToken);
        logAudit(request.getMobileNumber(), "LOGIN", "SUCCESS");

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(rawRefreshToken)
                .userId(user.getId().toString())
                .role(user.getRole().name())
                .build();
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request) {
        // Find token logic goes here. Hashing the raw token and matching.
        // For brevity in blueprint implementation, assume valid.
        throw new UnsupportedOperationException("Refresh logic to be implemented fully");
    }

    @Transactional
    public void logout(LogoutRequest request) {
        // Find by hash and set revoked = true
    }

    private User createNewUser(String mobile) {
        User user = User.builder()
                .mobileNumber(mobile)
                .role(Role.CLIENT)
                .status(UserStatus.ACTIVE)
                .build();
        return userRepository.save(user);
    }

    private DeviceSession createDeviceSession(User user, String deviceId) {
        DeviceSession session = DeviceSession.builder()
                .user(user)
                .deviceId(deviceId)
                .isActive(true)
                .build();
        return deviceSessionRepository.save(session);
    }

    private void saveRefreshToken(User user, DeviceSession session, String rawToken) {
        RefreshToken token = RefreshToken.builder()
                .user(user)
                .deviceSession(session)
                .tokenHash(passwordEncoder.encode(rawToken))
                .expiresAt(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    private void logAudit(String mobile, String action, String status) {
        AuthenticationAudit audit = AuthenticationAudit.builder()
                .mobileNumber(mobile)
                .action(action)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();
        auditRepository.save(audit);
    }
}
