CREATE TABLE otps (
    id VARCHAR(36) PRIMARY KEY,
    mobile_number VARCHAR(20) NOT NULL,
    otp VARCHAR(10) NOT NULL,
    expiry_time DATETIME NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    attempts INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_otp_mobile ON otps(mobile_number);
