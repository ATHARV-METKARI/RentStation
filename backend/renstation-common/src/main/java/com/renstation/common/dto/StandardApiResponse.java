package com.renstation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard generic API response wrapper.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String correlationId;
}
