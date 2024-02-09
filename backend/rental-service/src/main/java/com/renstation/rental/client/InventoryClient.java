package com.renstation.rental.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;
import java.util.Map;

@FeignClient(name = "inventory-service", path = "/api/v1/listings")
public interface InventoryClient {
    
    @PatchMapping("/{id}/status")
    void updateListingStatus(@PathVariable("id") UUID id, @RequestBody Map<String, String> payload);
}
