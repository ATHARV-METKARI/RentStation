package com.renstation.wishlist.client;
import com.renstation.common.dto.StandardApiResponse;
import com.renstation.wishlist.dto.GenericItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "game-service", path = "/api/v1/games")
public interface GameClient {
    @GetMapping("/{id}")
    StandardApiResponse<GenericItemDto> getGameById(@PathVariable("id") UUID id);
}
