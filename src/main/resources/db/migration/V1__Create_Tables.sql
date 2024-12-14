CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         permissao VARCHAR(20)
);

CREATE TABLE album (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       genero VARCHAR(50),
                       artista VARCHAR(100),
                       ano INT,
                       nota_media FLOAT,
                       imagem text
);

CREATE TABLE avaliacao (
                           id SERIAL PRIMARY KEY,
                           nota FLOAT,
                           comentario TEXT,
                           usuario_id INT REFERENCES usuario(id),
                           album_id INT REFERENCES album(id)
);
