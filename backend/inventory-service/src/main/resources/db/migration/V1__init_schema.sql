CREATE TABLE playstation_accounts (
    id VARCHAR(36) PRIMARY KEY,
    owner_id VARCHAR(36) NOT NULL,
    psn_online_id VARCHAR(100) NOT NULL,
    region VARCHAR(50),
    country VARCHAR(50),
    account_status VARCHAR(30) NOT NULL,
    expert_verified BOOLEAN NOT NULL DEFAULT FALSE,
    submitted_date DATETIME,
    approved_date DATETIME,
    remarks TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);
CREATE INDEX idx_ps_acc_owner ON playstation_accounts(owner_id);

CREATE TABLE game_listings (
    id VARCHAR(36) PRIMARY KEY,
    owner_id VARCHAR(36) NOT NULL,
    game_id VARCHAR(36) NOT NULL,
    playstation_account_id VARCHAR(36) NOT NULL,
    rental_type VARCHAR(20) NOT NULL,
    rental_price DECIMAL(10,2) NOT NULL,
    security_deposit DECIMAL(10,2) NOT NULL,
    availability BOOLEAN NOT NULL DEFAULT TRUE,
    listing_status VARCHAR(30) NOT NULL,
    expert_approval_status VARCHAR(30),
    expert_remarks TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_ps_account FOREIGN KEY (playstation_account_id) REFERENCES playstation_accounts(id)
);
CREATE INDEX idx_listing_owner ON game_listings(owner_id);
CREATE INDEX idx_listing_game ON game_listings(game_id);
CREATE INDEX idx_listing_status ON game_listings(listing_status);
CREATE INDEX idx_listing_type ON game_listings(rental_type);
