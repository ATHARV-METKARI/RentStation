package com.renstation.user.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserProfileDto {
    private UUID id;
    private String mobileNumber;
    
    @Size(min = 2, max = 50)
    private String displayName;
    
    @Size(max = 20)
    private String nickname;
    
    @Size(max = 500)
    private String bio;
    
    private String gender;
    
    @Past
    private LocalDate dateOfBirth;
    
    private String avatarUrl;
    private String status;
}
