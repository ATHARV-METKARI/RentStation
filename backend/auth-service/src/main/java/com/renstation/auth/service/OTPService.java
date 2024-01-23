package com.renstation.auth.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    private static final String OTP_PREFIX = "otp:";
    private static final String OTP_ATTEMPT_PREFIX = "otp_attempts:";
    private static final String OTP_BLOCK_PREFIX = "otp_block:";
    private static final long OTP_TTL = 3; // minutes
    private static final int MAX_ATTEMPTS = 3;

    public void generateAndSendOTP(String mobileNumber) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(OTP_BLOCK_PREFIX + mobileNumber))) {
            throw new BusinessException("Too many failed attempts. Try again later.");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        String hashedOtp = passwordEncoder.encode(otp);

        redisTemplate.opsForValue().set(OTP_PREFIX + mobileNumber, hashedOtp, Duration.ofMinutes(OTP_TTL));
        redisTemplate.delete(OTP_ATTEMPT_PREFIX + mobileNumber); // Reset attempts

        // In a real scenario, integrate with Twilio/AWS SNS here.
        System.out.println("====== SMS SENT ======");
        System.out.println("Mobile: " + mobileNumber + " | OTP: " + otp);
        System.out.println("======================");
    }

    public void verifyOTP(String mobileNumber, String inputOtp) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(OTP_BLOCK_PREFIX + mobileNumber))) {
            throw new BusinessException("Too many failed attempts. Try again later.");
        }

        String hashedOtp = (String) redisTemplate.opsForValue().get(OTP_PREFIX + mobileNumber);
        if (hashedOtp == null) {
            throw new UnauthorizedException("OTP expired or invalid");
        }

        if (!passwordEncoder.matches(inputOtp, hashedOtp)) {
            Long attempts = redisTemplate.opsForValue().increment(OTP_ATTEMPT_PREFIX + mobileNumber);
            if (attempts != null && attempts >= MAX_ATTEMPTS) {
                redisTemplate.opsForValue().set(OTP_BLOCK_PREFIX + mobileNumber, "BLOCKED", Duration.ofMinutes(15));
                redisTemplate.delete(OTP_PREFIX + mobileNumber);
                throw new BusinessException("Account blocked for 15 minutes due to too many failed attempts.");
            }
            throw new UnauthorizedException("Invalid OTP");
        }

        redisTemplate.delete(OTP_PREFIX + mobileNumber);
        redisTemplate.delete(OTP_ATTEMPT_PREFIX + mobileNumber);
    }
}
