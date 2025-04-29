CREATE TABLE IF NOT EXISTS tb_modules (
    module_id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    title VARCHAR(150) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    course_id BINARY(16) NOT NULL,
    INDEX idx_module_title (title),
    INDEX idx_course_id (course_id),
    CONSTRAINT fk_module_course FOREIGN KEY (course_id) 
        REFERENCES tb_courses (course_id) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;