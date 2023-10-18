CREATE TABLE games (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    platform VARCHAR(50) NOT NULL,
    genre VARCHAR(100),
    publisher VARCHAR(100),
    developer VARCHAR(100),
    release_date DATE,
    cover_image VARCHAR(255),
    description TEXT,
    pegi_rating VARCHAR(10),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);
CREATE INDEX idx_game_title ON games(title);
