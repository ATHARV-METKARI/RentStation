CREATE TABLE rentals (
    id VARCHAR(36) PRIMARY KEY,
    listing_id VARCHAR(36) NOT NULL,
    owner_id VARCHAR(36) NOT NULL,
    renter_id VARCHAR(36) NOT NULL,
    rental_start DATETIME,
    rental_end DATETIME,
    rental_status VARCHAR(30) NOT NULL,
    return_status VARCHAR(30),
    payment_status VARCHAR(30),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);
CREATE INDEX idx_rental_listing ON rentals(listing_id);
CREATE INDEX idx_rental_renter ON rentals(renter_id);
CREATE INDEX idx_rental_owner ON rentals(owner_id);
