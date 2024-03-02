package com.renstation.review.client;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.review.dto.RentalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "rental-service", path = "/api/v1/rentals")
public interface RentalClient {
    
    @GetMapping("/{id}")
    StandardApiResponse<RentalDto> getRentalById(@PathVariable("id") UUID id);
}
