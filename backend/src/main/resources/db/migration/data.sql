INSERT INTO users (username, email, password_hash, created_at, updated_at)
VALUES ('vasya', 'vasya@mail.ru', '$2a$10$hashedpass', NOW(), NOW())
ON CONFLICT (username) DO NOTHING;

-- Тесты
INSERT INTO tests (id, name, user_id, description, created_at, updated_at)
VALUES (1, 'Математика', 1, 'Тест по математике', NOW(), NOW()),
       (2, 'Русский язык', 1, 'Тест по русскому языку', NOW(), NOW()),
       (3, 'Физика', 1, 'Тест по физике', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Вопросы Математика (10)
INSERT INTO questions (id, test_id, content, type, created_at, updated_at)
VALUES (1, 1, 'Вопрос 1 Математика', 'CHOICE', NOW(), NOW()),
       (2, 1, 'Вопрос 2 Математика', 'CHOICE', NOW(), NOW()),
       (3, 1, 'Вопрос 3 Математика', 'INPUT', NOW(), NOW()),
       (4, 1, 'Вопрос 4 Математика', 'CHOICE', NOW(), NOW()),
       (5, 1, 'Вопрос 5 Математика', 'CHOICE', NOW(), NOW()),
       (6, 1, 'Вопрос 6 Математика', 'INPUT', NOW(), NOW()),
       (7, 1, 'Вопрос 7 Математика', 'CHOICE', NOW(), NOW()),
       (8, 1, 'Вопрос 8 Математика', 'CHOICE', NOW(), NOW()),
       (9, 1, 'Вопрос 9 Математика', 'INPUT', NOW(), NOW()),
       (10, 1, 'Вопрос 10 Математика', 'CHOICE', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Вопросы Русский язык (5)
INSERT INTO questions (id, test_id, content, type, created_at, updated_at)
VALUES (11, 2, 'Вопрос 1 Русский язык', 'CHOICE', NOW(), NOW()),
       (12, 2, 'Вопрос 2 Русский язык', 'INPUT', NOW(), NOW()),
       (13, 2, 'Вопрос 3 Русский язык', 'CHOICE', NOW(), NOW()),
       (14, 2, 'Вопрос 4 Русский язык', 'CHOICE', NOW(), NOW()),
       (15, 2, 'Вопрос 5 Русский язык', 'INPUT', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Вопросы Физика (3)
INSERT INTO questions (id, test_id, content, type, created_at, updated_at)
VALUES (16, 3, 'Вопрос 1 Физика', 'CHOICE', NOW(), NOW()),
       (17, 3, 'Вопрос 2 Физика', 'INPUT', NOW(), NOW()),
       (18, 3, 'Вопрос 3 Физика', 'CHOICE', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Вопр.1 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (1, 'Ответ 1', TRUE, NOW(), NOW()),
       (1, 'Ответ 2', FALSE, NOW(), NOW()),
       (1, 'Ответ 3', FALSE, NOW(), NOW()),
       (1, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.2 CHOICE (2 правильных)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (2, 'Ответ 1', TRUE, NOW(), NOW()),
       (2, 'Ответ 2', TRUE, NOW(), NOW()),
       (2, 'Ответ 3', FALSE, NOW(), NOW()),
       (2, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.3 INPUT (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (3, 'Ответ для Вопр.3', TRUE, NOW(), NOW());

-- Вопр.4 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (4, 'Ответ 1', FALSE, NOW(), NOW()),
       (4, 'Ответ 2', TRUE, NOW(), NOW()),
       (4, 'Ответ 3', FALSE, NOW(), NOW()),
       (4, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.5 CHOICE (2 правильных)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (5, 'Ответ 1', TRUE, NOW(), NOW()),
       (5, 'Ответ 2', TRUE, NOW(), NOW()),
       (5, 'Ответ 3', FALSE, NOW(), NOW()),
       (5, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.6 INPUT
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (6, 'Ответ для Вопр.6', TRUE, NOW(), NOW());

-- Вопр.7 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (7, 'Ответ 1', FALSE, NOW(), NOW()),
       (7, 'Ответ 2', TRUE, NOW(), NOW()),
       (7, 'Ответ 3', FALSE, NOW(), NOW()),
       (7, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.8 CHOICE (2 правильных)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (8, 'Ответ 1', TRUE, NOW(), NOW()),
       (8, 'Ответ 2', TRUE, NOW(), NOW()),
       (8, 'Ответ 3', FALSE, NOW(), NOW()),
       (8, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.9 INPUT
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (9, 'Ответ для Вопр.9', TRUE, NOW(), NOW());

-- Вопр.10 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (10, 'Ответ 1', FALSE, NOW(), NOW()),
       (10, 'Ответ 2', TRUE, NOW(), NOW()),
       (10, 'Ответ 3', FALSE, NOW(), NOW()),
       (10, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.11 CHOICE (2 правильных)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (11, 'Ответ 1', TRUE, NOW(), NOW()),
       (11, 'Ответ 2', TRUE, NOW(), NOW()),
       (11, 'Ответ 3', FALSE, NOW(), NOW()),
       (11, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.12 INPUT
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (12, 'Ответ для Вопр.12', TRUE, NOW(), NOW());

-- Вопр.13 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (13, 'Ответ 1', FALSE, NOW(), NOW()),
       (13, 'Ответ 2', TRUE, NOW(), NOW()),
       (13, 'Ответ 3', FALSE, NOW(), NOW()),
       (13, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.14 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (14, 'Ответ 1', TRUE, NOW(), NOW()),
       (14, 'Ответ 2', FALSE, NOW(), NOW()),
       (14, 'Ответ 3', FALSE, NOW(), NOW()),
       (14, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.15 INPUT
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (15, 'Ответ для Вопр.15', TRUE, NOW(), NOW());

-- Ответы для Физика

-- Вопр.16 CHOICE (1 правильный)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (16, 'Ответ 1', FALSE, NOW(), NOW()),
       (16, 'Ответ 2', TRUE, NOW(), NOW()),
       (16, 'Ответ 3', FALSE, NOW(), NOW()),
       (16, 'Ответ 4', FALSE, NOW(), NOW());

-- Вопр.17 INPUT
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (17, 'Ответ для Вопр.17', TRUE, NOW(), NOW());

-- Вопр.18 CHOICE (2 правильных)
INSERT INTO answers (question_id, content, is_correct, created_at, updated_at)
VALUES (18, 'Ответ 1', TRUE, NOW(), NOW()),
       (18, 'Ответ 2', TRUE, NOW(), NOW()),
       (18, 'Ответ 3', FALSE, NOW(), NOW()),
       (18, 'Ответ 4', FALSE, NOW(), NOW());
