package com.renstation.auth.dto;
import com.renstation.common.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendOtpRequest {
    @NotBlank
    @PhoneNumber
    private String mobileNumber;
}
