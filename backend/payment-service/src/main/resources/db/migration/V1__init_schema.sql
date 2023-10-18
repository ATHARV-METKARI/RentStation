CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY,
    rental_id VARCHAR(36) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    platform_fee DECIMAL(10,2) NOT NULL,
    owner_earnings DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50),
    payment_status VARCHAR(30) NOT NULL,
    transaction_reference VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_payment_rental ON payments(rental_id);
