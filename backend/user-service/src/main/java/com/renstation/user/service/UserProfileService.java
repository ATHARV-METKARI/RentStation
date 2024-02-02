package com.renstation.user.service;

import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.user.dto.UserProfileDto;
import com.renstation.user.entity.UserProfile;
import com.renstation.user.entity.UserStatistics;
import com.renstation.user.repository.UserProfileRepository;
import com.renstation.user.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    
    private final UserProfileRepository userProfileRepository;
    private final UserStatisticsRepository statisticsRepository;

    @Transactional(readOnly = true)
    public UserProfile getProfileByUserId(UUID userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for userId: " + userId));
    }

    @Transactional
    public UserProfile createOrUpdateProfile(UUID userId, UserProfileDto dto) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile p = UserProfile.builder()
                            .userId(userId)
                            .mobileNumber(dto.getMobileNumber()) // Provided by internal token/auth context initially
                            .status("ACTIVE")
                            .build();
                    // Create default stats
                    UserStatistics stats = UserStatistics.builder()
                            .userProfile(p)
                            .completedRentals(0)
                            .cancelledRentals(0)
                            .totalEarnings(BigDecimal.ZERO)
                            .expertDisputesResolved(0)
                            .build();
                    statisticsRepository.save(stats);
                    return p;
                });
        
        if (dto.getDisplayName() != null) profile.setDisplayName(dto.getDisplayName());
        if (dto.getNickname() != null) profile.setNickname(dto.getNickname());
        if (dto.getBio() != null) profile.setBio(dto.getBio());
        if (dto.getGender() != null) profile.setGender(dto.getGender());
        if (dto.getDateOfBirth() != null) profile.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getAvatarUrl() != null) profile.setAvatarUrl(dto.getAvatarUrl());

        return userProfileRepository.save(profile);
    }

    @Transactional
    public void changeStatus(UUID userId, String status) {
        UserProfile profile = getProfileByUserId(userId);
        profile.setStatus(status);
        userProfileRepository.save(profile);
    }
}
