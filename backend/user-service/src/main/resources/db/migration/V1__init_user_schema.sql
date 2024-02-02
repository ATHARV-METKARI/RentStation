
CREATE TABLE user_profiles (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL UNIQUE,
    mobile_number VARCHAR(15) NOT NULL UNIQUE,
    display_name VARCHAR(50),
    nickname VARCHAR(20),
    bio VARCHAR(500),
    gender VARCHAR(20),
    date_of_birth DATE,
    avatar_url VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE user_addresses (
    id BINARY(16) NOT NULL,
    user_profile_id BINARY(16) NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (user_profile_id) REFERENCES user_profiles(id)
);

CREATE TABLE user_statistics (
    id BINARY(16) NOT NULL,
    user_profile_id BINARY(16) NOT NULL UNIQUE,
    seller_rating DECIMAL(3,2),
    completed_rentals INT NOT NULL DEFAULT 0,
    cancelled_rentals INT NOT NULL DEFAULT 0,
    total_earnings DECIMAL(10,2) DEFAULT 0.00,
    expert_disputes_resolved INT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (user_profile_id) REFERENCES user_profiles(id)
);

CREATE TABLE user_preferences (
    id BINARY(16) NOT NULL,
    user_profile_id BINARY(16) NOT NULL UNIQUE,
    preferred_language VARCHAR(10),
    timezone VARCHAR(50),
    email_notifications BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id),
    FOREIGN KEY (user_profile_id) REFERENCES user_profiles(id)
);
