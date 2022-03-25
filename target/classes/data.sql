INSERT INTO user (id, first_name, last_name, age, login, email, password_hash, is_deleted) VALUES
-- Hashed "!2e4S"
  (1, 'Admin', 'Admin', 25, 'testAdmin', 'admin@admin.admin', '$2a$10$zRVR3lJHav.qyQVX7KwX.ujRnVUopCqiV6O4BzWJN2IvA2X/mImPm', false),
-- Hashed "12345"
  (2, 'User1', 'User1', 20, 'testUser1', 'user@user.user', '$2a$10$zRVR3lJHav.qyQVX7KwX.ujRnVUopCqiV6O4BzWJN2IvA2X/mImPm', false);


INSERT INTO role (id, name, is_default) VALUES
  (1, 'USER', 1), (2, 'ADMIN', 0);

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 2), (2, 1);



