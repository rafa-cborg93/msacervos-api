CREATE TABLE acervo (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       isbn VARCHAR(20) NOT NULL,
                       numero_chamada VARCHAR(20) NOT NULL,
                       autor VARCHAR(20) NOT NULL,
                       titulo VARCHAR(30) NOT NULL,
                       imprenta VARCHAR(100) NOT NULL,
                       formato_fisico VARCHAR(100) NOT NULL,
                       assuntos VARCHAR(100) NOT NULL,
                       outro_autores VARCHAR(100) NOT NULL,
                       data_cadastro DATE NOT NULL,
                       data_atualizacao DATE NOT NULL,
                       PRIMARY KEY (id));