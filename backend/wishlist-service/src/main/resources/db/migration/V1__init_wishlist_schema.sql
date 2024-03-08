
CREATE TABLE wishlists (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE wishlist_items (
    id BINARY(16) NOT NULL,
    wishlist_id BINARY(16) NOT NULL,
    game_id BINARY(16),
    listing_id BINARY(16),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id),
    CONSTRAINT chk_wishlist_item CHECK (
        (game_id IS NOT NULL AND listing_id IS NULL) OR 
        (game_id IS NULL AND listing_id IS NOT NULL)
    )
);

CREATE UNIQUE INDEX idx_wishlist_game ON wishlist_items(wishlist_id, game_id) WHERE game_id IS NOT NULL;
CREATE UNIQUE INDEX idx_wishlist_listing ON wishlist_items(wishlist_id, listing_id) WHERE listing_id IS NOT NULL;
