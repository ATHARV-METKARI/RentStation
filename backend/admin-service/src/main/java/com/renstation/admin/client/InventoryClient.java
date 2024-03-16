package com.renstation.admin.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@FeignClient(name = "inventory-service", path = "/api/v1/inventory")
public interface InventoryClient {
    @PatchMapping("/{id}/approval")
    Object updateListingApproval(@PathVariable("id") UUID id, @RequestParam("status") String status);
}
