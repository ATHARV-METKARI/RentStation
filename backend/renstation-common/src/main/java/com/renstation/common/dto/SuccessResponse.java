package com.renstation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard success response without generic data payload.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
    private boolean success;
    private String message;
}
