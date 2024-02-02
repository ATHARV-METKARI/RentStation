package com.renstation.user.entity;

import com.renstation.common.entity.UUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference extends UUIDEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false, unique = true)
    private UserProfile userProfile;

    @Column(name = "preferred_language", length = 10)
    private String preferredLanguage;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "email_notifications")
    private boolean emailNotifications;
}
