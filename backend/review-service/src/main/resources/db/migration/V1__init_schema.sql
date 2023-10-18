CREATE TABLE reviews (
    id VARCHAR(36) PRIMARY KEY,
    rental_id VARCHAR(36) NOT NULL,
    reviewer_id VARCHAR(36) NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_review_rental ON reviews(rental_id);
CREATE INDEX idx_review_reviewer ON reviews(reviewer_id);
