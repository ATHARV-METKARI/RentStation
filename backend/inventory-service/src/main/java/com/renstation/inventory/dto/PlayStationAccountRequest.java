package com.renstation.inventory.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayStationAccountRequest {
    @NotBlank
    private String psnOnlineId;
    @NotBlank
    private String region;
    @NotBlank
    private String country;
}
