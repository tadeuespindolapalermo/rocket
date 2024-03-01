INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_CANDIDATO');

-- senha 123 criptografada para usuário administrador (admin)
INSERT INTO usuario (id, login, senha) VALUES (1, 'admin', '$2a$10$x3MmbxDhEmM0P8SWL.N7uuQ5NgFwc/.yTaq6q1SSqfjc.R7sxOp3a');

INSERT INTO usuarios_role (usuario_id, role_id) VALUES (1, 1);
