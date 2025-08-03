DELETE FROM restaurante;
DELETE FROM horarios_de_funcionamento;
DELETE FROM horarios;
DELETE FROM endereco;
ALTER TABLE endereco ALTER COLUMN id RESTART WITH 1;
DELETE FROM usuario;
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 1;
