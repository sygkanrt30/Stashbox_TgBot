CREATE TABLE IF NOT EXISTS section
(
    id VARCHAR(64) PRIMARY KEY,
    title VARCHAR(25) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_section_user_title UNIQUE (user_id, title),
    CONSTRAINT fk_section_user FOREIGN KEY (user_id) REFERENCES tg_user (id)
);
