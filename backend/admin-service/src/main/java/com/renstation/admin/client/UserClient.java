package com.renstation.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Map;

@FeignClient(name = "user-service", path = "/api/v1/users")
public interface UserClient {
    @PatchMapping("/{id}/status")
    Object updateUserStatus(@PathVariable("id") UUID id, @RequestParam("status") String status);
    
    @PatchMapping("/{id}/role")
    Object updateUserRole(@PathVariable("id") UUID id, @RequestParam("role") String role);
}
