package com.renstation.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@FeignClient(name = "rental-service", path = "/api/v1/rentals")
public interface RentalClient {
    
    @PatchMapping("/{id}/status")
    void updateRentalStatus(@PathVariable("id") UUID id, @RequestParam("status") String status, @RequestParam("remarks") String remarks);
}
