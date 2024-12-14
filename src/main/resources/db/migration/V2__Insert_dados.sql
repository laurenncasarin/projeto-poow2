-- Inserts for usuario table
INSERT INTO usuario (nome, email, senha, permissao) VALUES ('John Doe', 'john.doe@example.com', '$2a$10$TJ4r2Dmapjb3STIxaCyXYuAuHMT1Z/ymIS0vCRr.hbxxKWo302nXC', 'USER');
INSERT INTO usuario (nome, email, senha, permissao) VALUES ('Jane Smith', 'jane.smith@example.com', '$2a$10$TJ4r2Dmapjb3STIxaCyXYuAuHMT1Z/ymIS0vCRr.hbxxKWo302nXC', 'ADMIN');
INSERT INTO usuario (nome, email, senha, permissao) VALUES ('Alice Johnson', 'alice.johnson@example.com', '$2a$10$TJ4r2Dmapjb3STIxaCyXYuAuHMT1Z/ymIS0vCRr.hbxxKWo302nXC', 'USER');
INSERT INTO usuario (nome, email, senha, permissao) VALUES ('Bob Brown', 'bob.brown@example.com', '$2a$10$TJ4r2Dmapjb3STIxaCyXYuAuHMT1Z/ymIS0vCRr.hbxxKWo302nXC', 'USER');
INSERT INTO usuario (nome, email, senha, permissao) VALUES ('Charlie Davis', 'charlie.davis@example.com', '$2a$10$TJ4r2Dmapjb3STIxaCyXYuAuHMT1Z/ymIS0vCRr.hbxxKWo302nXC', 'USER');

-- Inserts for album table
INSERT INTO album (nome, genero, artista, ano, nota_media, imagem) VALUES ('Album 1', 'Rock', 'Artist 1', 2020, 4.5, 'image1.jpg');
INSERT INTO album (nome, genero, artista, ano, nota_media, imagem) VALUES ('Album 2', 'Pop', 'Artist 2', 2019, 4.0, 'image2.jpg');
INSERT INTO album (nome, genero, artista, ano, nota_media, imagem) VALUES ('Album 3', 'Jazz', 'Artist 3', 2018, 3.5, 'image3.jpg');

-- Inserts for avaliacao table
INSERT INTO avaliacao (nota, comentario, usuario_id, album_id) VALUES (4.5, 'Great album!', 1, 1);
INSERT INTO avaliacao (nota, comentario, usuario_id, album_id) VALUES (4.0, 'Good album!', 2, 2);
INSERT INTO avaliacao (nota, comentario, usuario_id, album_id) VALUES (3.5, 'Average album.', 3, 3);