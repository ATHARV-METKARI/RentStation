
CREATE TABLE rentals (
    id BINARY(16) NOT NULL,
    listing_id BINARY(16) NOT NULL,
    game_id BINARY(16) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    renter_id BINARY(16) NOT NULL,
    rental_type VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    deposit DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE rental_timelines (
    id BINARY(16) NOT NULL,
    rental_id BINARY(16) NOT NULL,
    status_from VARCHAR(50),
    status_to VARCHAR(50) NOT NULL,
    remarks VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);

CREATE TABLE rental_transactions (
    id BINARY(16) NOT NULL,
    rental_id BINARY(16) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    gateway_reference VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);

CREATE INDEX idx_rentals_renter ON rentals(renter_id);
CREATE INDEX idx_rentals_owner ON rentals(owner_id);
CREATE INDEX idx_rentals_status ON rentals(status);
