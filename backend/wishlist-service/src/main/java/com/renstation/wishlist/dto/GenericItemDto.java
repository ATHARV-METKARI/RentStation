package com.renstation.wishlist.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class GenericItemDto {
    private UUID id;
    private String title;
}
