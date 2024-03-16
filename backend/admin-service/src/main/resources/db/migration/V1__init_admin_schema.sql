
CREATE TABLE admin_audits (
    id BINARY(16) NOT NULL,
    admin_id BINARY(16) NOT NULL,
    action VARCHAR(255) NOT NULL,
    target_id VARCHAR(255),
    target_service VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);
