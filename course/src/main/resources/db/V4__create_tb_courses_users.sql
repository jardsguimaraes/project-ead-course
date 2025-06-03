CREATE TABLE IF NOT EXISTS tb_courses_users (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    user_id BINARY(16) NOT NULL,
    course_course_id BINARY(16) NOT NULL,
    CONSTRAINT fk_courses_users_user
        FOREIGN KEY (course_course_id) REFERENCES tb_courses (course_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_course_course_id (course_course_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
