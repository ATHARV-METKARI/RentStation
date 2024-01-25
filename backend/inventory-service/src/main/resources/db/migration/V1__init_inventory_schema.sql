
CREATE TABLE playstation_accounts (
    id BINARY(16) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    psn_online_id VARCHAR(255) NOT NULL,
    region VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    verification_status VARCHAR(50) NOT NULL,
    submitted_at TIMESTAMP,
    approved_at TIMESTAMP,
    expert_id BINARY(16),
    remarks VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE game_listings (
    id BINARY(16) NOT NULL,
    game_id BINARY(16) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    account_id BINARY(16) NOT NULL,
    listing_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2) NOT NULL,
    availability VARCHAR(50) NOT NULL,
    listing_status VARCHAR(50) NOT NULL,
    approval_status VARCHAR(50) NOT NULL,
    condition_notes VARCHAR(500),
    expert_id BINARY(16),
    approved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES playstation_accounts(id)
);

-- Unique constraint to prevent duplicate active listings for the same game and account
CREATE UNIQUE INDEX idx_unique_active_listing ON game_listings(account_id, game_id, listing_type) WHERE listing_status = 'ACTIVE';

CREATE TABLE listing_approvals (
    id BINARY(16) NOT NULL,
    listing_id BINARY(16) NOT NULL,
    expert_id BINARY(16) NOT NULL,
    status VARCHAR(50) NOT NULL,
    remarks VARCHAR(500),
    approved_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (listing_id) REFERENCES game_listings(id)
);
