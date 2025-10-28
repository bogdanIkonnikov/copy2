INSERT INTO users (username, email, password_hash, created_at, updated_at)
VALUES ('vasya', 'vasya@mail.ru', '$2a$10$hashedpass', NOW(), NOW())
    ON CONFLICT (username) DO NOTHING;