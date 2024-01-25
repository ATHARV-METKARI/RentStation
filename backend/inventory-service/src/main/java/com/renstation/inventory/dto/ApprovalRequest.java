package com.renstation.inventory.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovalRequest {
    @NotBlank
    private String status; // APPROVED, REJECTED
    private String remarks;
}
