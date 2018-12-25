-- pass: 123
insert into users (id, username, password, email, enabled, role)
values(1, 'username', '$2a$10$P3TF2Lec8SqabqQAItzwReZcDNSEfulnvXka9pgh6BIR3P8So4FAK', 'user@gmail.com', true, 'ROLE_USER');