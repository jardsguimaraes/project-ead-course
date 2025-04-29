CREATE TABLE IF NOT EXISTS tb_lessons (
    lesson_id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    title VARCHAR(150) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    video_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    module_id BINARY(16) NOT NULL,
    INDEX idx_lesson_title (title),
    INDEX idx_module_id (module_id),
    CONSTRAINT fk_lesson_module FOREIGN KEY (module_id) 
        REFERENCES tb_modules (module_id) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;