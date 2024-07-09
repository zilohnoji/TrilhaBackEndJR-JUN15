INSERT INTO tb_role (role) VALUES ('ROLE_USER');

INSERT INTO tb_user (id, name, email, password, enabled) VALUES (1, 'Pedro Donato', 'donatopedro.developer@gmail.com', 'senhasupersegura123', true);

INSERT INTO tb_task (id, title, content, status, user_id) VALUES (1, 'Estudar Java', 'Aprender como usar a stream api do Java', 'PROGRESS', 1);

INSERT INTO tb_email_code (id, code, expired_at, user_id) VALUES (1, 'megacodigoseguro', '2024-12-09 00:00:00', 1);

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);