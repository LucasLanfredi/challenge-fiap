
INSERT INTO usuario ( admin, data_ultima_alteracao, email, login, nome, senha, tipo_usuario)
VALUES ( true, CURRENT_TIMESTAMP, 'usuario@email.com', 'usuario_login', 'Nome do Usuário', 'senha123', 'CLIENTE'),
       (true, CURRENT_TIMESTAMP, 'admin@email.com', 'admin_login', 'Nome do Admin', 'senha123', 'DONO_RESTAURANTE');
ALTER TABLE endereco ALTER COLUMN id RESTART WITH 1;
INSERT INTO endereco (cep, cidade, estado, numero, rua, usuario_id)
VALUES ('12345-678', 'São Paulo', 'SP', '100', 'Rua das Flores', 1),
       ('87654-321', 'Rio de Janeiro', 'RJ', '200', 'Avenida Central', 2);

-- Inserindo horários
INSERT INTO horarios (id_horarios, abertura, fechamento) VALUES
    (1,'08:00', '18:00'), -- id_horarios = 1
    (2, '09:00', '15:00'), -- id_horarios = 2
    (3, '10:00', '14:00'), -- id_horarios = 3
    (4, '11:00', '20:00'), -- id_horarios = 4
    (5,'12:00', '22:00'), -- id_horarios = 5
    (6, '07:00', '12:00'); -- id_horarios = 6

-- Inserindo horários de funcionamento
INSERT INTO horarios_de_funcionamento (id_horarios_de_funcionamento, dias_uteis_id_horarios, domingoeferiado_id_horarios, sabado_id_horarios)
VALUES (1, 1, 2, 3), -- id_horarios_de_funcionamento = 1
       (2, 4, 5, 6); -- id_horarios_de_funcionamento = 2

-- Inserindo restaurantes
INSERT INTO restaurante (restaurante_id, nome, tipo_de_cozinha, usuario_id, endereco_id, horario_de_funcionamento_id_horarios_de_funcionamento)
VALUES (1, 'Restaurante A', 'Italiana', 2, 1, 1),
       (2, 'Restaurante B', 'Japonesa', 1, 2, 2);