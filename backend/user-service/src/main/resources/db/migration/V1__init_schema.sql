CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100),
    profile_photo VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    last_login DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    soft_delete BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX idx_user_mobile ON users(mobile_number);
