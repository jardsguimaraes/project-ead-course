CREATE TABLE IF NOT EXISTS tb_courses (
    course_id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(150) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    course_status VARCHAR(20) NOT NULL,
    course_level VARCHAR(20) NOT NULL,
    user_instructor BINARY(16) NOT NULL,
    image_url VARCHAR(255),
    INDEX idx_course_status (course_status),
    INDEX idx_course_level (course_level),
    INDEX idx_user_instructor (user_instructor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;