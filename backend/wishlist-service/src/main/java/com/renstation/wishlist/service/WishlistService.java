package com.renstation.wishlist.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.wishlist.client.GameClient;
import com.renstation.wishlist.client.InventoryClient;
import com.renstation.wishlist.dto.WishlistItemRequest;
import com.renstation.wishlist.entity.Wishlist;
import com.renstation.wishlist.entity.WishlistItem;
import com.renstation.wishlist.repository.WishlistItemRepository;
import com.renstation.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository itemRepository;
    private final GameClient gameClient;
    private final InventoryClient inventoryClient;

    public Wishlist getMyWishlist(UUID userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> wishlistRepository.save(Wishlist.builder().userId(userId).build()));
    }

    @Transactional
    public Wishlist addItem(UUID userId, WishlistItemRequest request) {
        Wishlist wishlist = getMyWishlist(userId);

        if (request.getGameId() == null && request.getListingId() == null) {
            throw new BusinessException("Must provide either gameId or listingId");
        }
        if (request.getGameId() != null && request.getListingId() != null) {
            throw new BusinessException("Cannot provide both gameId and listingId simultaneously");
        }

        if (request.getGameId() != null) {
            if (itemRepository.existsByWishlistIdAndGameId(wishlist.getId(), request.getGameId())) {
                throw new BusinessException("Game already in wishlist");
            }
            try {
                gameClient.getGameById(request.getGameId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Game does not exist");
            }
        }

        if (request.getListingId() != null) {
            if (itemRepository.existsByWishlistIdAndListingId(wishlist.getId(), request.getListingId())) {
                throw new BusinessException("Listing already in wishlist");
            }
            try {
                inventoryClient.getListingById(request.getListingId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Listing does not exist");
            }
        }

        WishlistItem item = WishlistItem.builder()
                .gameId(request.getGameId())
                .listingId(request.getListingId())
                .createdAt(LocalDateTime.now())
                .build();
                
        wishlist.addItem(item);
        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeItem(UUID userId, UUID itemId) {
        Wishlist wishlist = getMyWishlist(userId);
        boolean removed = wishlist.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new ResourceNotFoundException("Item not found in wishlist");
        }
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void clearWishlist(UUID userId) {
        Wishlist wishlist = getMyWishlist(userId);
        wishlist.getItems().clear();
        wishlistRepository.save(wishlist);
    }
}
