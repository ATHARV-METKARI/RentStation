
CREATE TABLE reviews (
    id BINARY(16) NOT NULL,
    rental_id BINARY(16) NOT NULL,
    reviewer_id BINARY(16) NOT NULL,
    reviewed_user_id BINARY(16),
    listing_id BINARY(16),
    rating INT NOT NULL,
    comment VARCHAR(1000),
    review_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

-- Anti-spam: A user can only leave one review per rental transaction
CREATE UNIQUE INDEX idx_unique_review ON reviews(rental_id, reviewer_id) WHERE deleted = false;

CREATE TABLE rating_aggregates (
    id BINARY(16) NOT NULL,
    target_id BINARY(16) NOT NULL UNIQUE,
    target_type VARCHAR(50) NOT NULL,
    average_rating DECIMAL(3,2) NOT NULL,
    total_reviews INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE INDEX idx_reviews_reviewed_user ON reviews(reviewed_user_id);
CREATE INDEX idx_reviews_listing ON reviews(listing_id);
