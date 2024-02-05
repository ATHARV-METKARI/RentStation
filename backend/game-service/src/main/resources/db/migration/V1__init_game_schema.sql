
CREATE TABLE games (
    id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    platform VARCHAR(50) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    developer VARCHAR(100),
    release_date DATE,
    cover_image VARCHAR(500),
    thumbnail VARCHAR(500),
    description TEXT,
    rating DECIMAL(3,2),
    tags VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_unique_title_platform ON games(title, platform) WHERE deleted = false;
CREATE INDEX idx_games_title ON games(title);
CREATE INDEX idx_games_genre ON games(genre);
