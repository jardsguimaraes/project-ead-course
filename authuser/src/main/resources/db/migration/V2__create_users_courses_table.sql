CREATE TABLE IF NOT EXISTS tb_users_courses (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    course_id BINARY(16) NOT NULL,
    user_user_id BINARY(16) NOT NULL,
    CONSTRAINT fk_users_courses_user
        FOREIGN KEY (user_user_id) REFERENCES tb_users (user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_user_user_id (user_user_id),
    INDEX idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
