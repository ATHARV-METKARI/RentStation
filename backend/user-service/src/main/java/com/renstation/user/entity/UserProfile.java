package com.renstation.user.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId; // This is the ID from auth-service

    @Column(name = "mobile_number", nullable = false, unique = true, length = 15)
    private String mobileNumber;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "status", nullable = false)
    private String status; // ACTIVE, SUSPENDED
}
