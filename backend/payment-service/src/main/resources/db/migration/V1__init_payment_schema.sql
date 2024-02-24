
CREATE TABLE payments (
    id BINARY(16) NOT NULL,
    rental_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    platform_fee DECIMAL(10,2) NOT NULL,
    seller_earning DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    idempotency_key VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE payment_transactions (
    id BINARY(16) NOT NULL,
    payment_id BINARY(16) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    gateway_reference VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);

CREATE TABLE refunds (
    id BINARY(16) NOT NULL,
    payment_id BINARY(16) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    reason VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    gateway_reference VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);

CREATE INDEX idx_payments_rental ON payments(rental_id);
CREATE INDEX idx_payments_user ON payments(user_id);
