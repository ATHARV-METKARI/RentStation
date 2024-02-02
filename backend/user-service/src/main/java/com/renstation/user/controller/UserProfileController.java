package com.renstation.user.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.user.dto.UserProfileDto;
import com.renstation.user.entity.UserProfile;
import com.renstation.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<StandardApiResponse<UserProfileDto>> getMyProfile(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        UserProfile profile = userProfileService.getProfileByUserId(userId);
        
        UserProfileDto dto = mapToDto(profile);
        return ResponseEntity.ok(StandardApiResponse.<UserProfileDto>builder()
                .success(true)
                .data(dto)
                .build());
    }

    @PutMapping("/me")
    public ResponseEntity<StandardApiResponse<UserProfileDto>> updateMyProfile(
            Authentication authentication, 
            @Valid @RequestBody UserProfileDto dto) {
        
        UUID userId = UUID.fromString(authentication.getName());
        UserProfile profile = userProfileService.createOrUpdateProfile(userId, dto);
        
        return ResponseEntity.ok(StandardApiResponse.<UserProfileDto>builder()
                .success(true)
                .data(mapToDto(profile))
                .build());
    }

    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Void>> changeStatus(
            @PathVariable UUID userId, 
            @RequestParam String status) {
        
        userProfileService.changeStatus(userId, status);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder()
                .success(true)
                .message("Status updated successfully")
                .build());
    }
    
    private UserProfileDto mapToDto(UserProfile profile) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(profile.getId());
        dto.setMobileNumber(profile.getMobileNumber());
        dto.setDisplayName(profile.getDisplayName());
        dto.setNickname(profile.getNickname());
        dto.setBio(profile.getBio());
        dto.setGender(profile.getGender());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setAvatarUrl(profile.getAvatarUrl());
        dto.setStatus(profile.getStatus());
        return dto;
    }
}
