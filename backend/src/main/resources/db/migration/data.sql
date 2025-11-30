CREATE SEQUENCE tests_id_seq;
CREATE SEQUENCE test_sessions_id_seq;
CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE answers_id_seq;
CREATE SEQUENCE questions_id_seq;

CREATE TABLE users (
                       id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
                       username VARCHAR(255) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

CREATE TABLE tests (
                       id INTEGER PRIMARY KEY DEFAULT nextval('tests_id_seq'),
                       test_name VARCHAR(255) NOT NULL,
                       user_id INTEGER,
                       description TEXT,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       CONSTRAINT fk_tests_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE questions (
                           id INTEGER PRIMARY KEY DEFAULT nextval('questions_id_seq'),
                           test_id INTEGER,
                           content TEXT NOT NULL,
                           question_type VARCHAR(255),
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,
                           CONSTRAINT fk_questions_test FOREIGN KEY(test_id) REFERENCES tests(id) ON DELETE CASCADE
);

CREATE TABLE answers (
                         id INTEGER PRIMARY KEY DEFAULT nextval('answers_id_seq'),
                         question_id INTEGER,
                         content TEXT NOT NULL,
                         is_correct BOOLEAN NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL,
                         CONSTRAINT fk_answers_question FOREIGN KEY(question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE test_sessions (
                               id INTEGER PRIMARY KEY DEFAULT nextval('test_sessions_id_seq'),
                               user_id INTEGER,
                               test_id INTEGER,
                               correct_count BIGINT NOT NULL DEFAULT 0,
                               total_count BIGINT NOT NULL DEFAULT 0,
                               started_at TIMESTAMP NOT NULL,
                               finished_at TIMESTAMP,
                               CONSTRAINT fk_test_sessions_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL,
                               CONSTRAINT fk_test_sessions_test FOREIGN KEY(test_id) REFERENCES tests(id) ON DELETE SET NULL
);

INSERT INTO users (username, email, password_hash, created_at, updated_at)
VALUES ('vasya', 'vasya@mail.ru', '$2a$10$hashedpass', NOW(), NOW())
    ON CONFLICT (username) DO NOTHING;

-- Тесты
INSERT INTO tests (test_name, user_id, description, created_at, updated_at)
VALUES ('Математика', 1, 'Тест по математике', NOW(), NOW()),
       ('Русский язык', 1, 'Тест по русскому языку', NOW(), NOW()),
       ('Физика', 1, 'Тест по физике', NOW(), NOW());

-- Вопросы Математика (10)
INSERT INTO questions (test_id, content, question_type, created_at, updated_at)
VALUES (1, 'Вопрос 1 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 2 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 3 Математика', 'INPUT', NOW(), NOW()),
       (1, 'Вопрос 4 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 5 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 6 Математика', 'INPUT', NOW(), NOW()),
       (1, 'Вопрос 7 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 8 Математика', 'CHOICE', NOW(), NOW()),
       (1, 'Вопрос 9 Математика', 'INPUT', NOW(), NOW()),
       (1, 'Вопрос 10 Математика', 'CHOICE', NOW(), NOW());

-- Вопросы Русский язык (5)
INSERT INTO questions (test_id, content, question_type, created_at, updated_at)
VALUES (2, 'Вопрос 1 Русский язык', 'CHOICE', NOW(), NOW()),
       (2, 'Вопрос 2 Русский язык', 'INPUT', NOW(), NOW()),
       (2, 'Вопрос 3 Русский язык', 'CHOICE', NOW(), NOW()),
       (2, 'Вопрос 4 Русский язык', 'CHOICE', NOW(), NOW()),
       (2, 'Вопрос 5 Русский язык', 'INPUT', NOW(), NOW());

-- Вопросы Физика (3)
INSERT INTO questions (test_id, content, question_type, created_at, updated_at)
VALUES (3, 'Вопрос 1 Физика', 'CHOICE', NOW(), NOW()),
       (3, 'Вопрос 2 Физика', 'INPUT', NOW(), NOW()),
       (3, 'Вопрос 3 Физика', 'CHOICE', NOW(), NOW());

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

SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));
SELECT setval('answers_id_seq', (SELECT MAX(id) FROM answers));