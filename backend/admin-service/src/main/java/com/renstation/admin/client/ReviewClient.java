package com.renstation.admin.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@FeignClient(name = "review-service", path = "/api/v1/reviews")
public interface ReviewClient {
    @DeleteMapping("/{id}")
    Object deleteReview(@PathVariable("id") UUID id);
}
