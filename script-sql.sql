DROP DATABASE IF EXISTS crud_manager;

CREATE DATABASE IF NOT EXISTS crud_manager;

USE crud_manager;
/* select * from users;
select * from posts;
select * from companies;
select * from movies;
select * from tickets; */

CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo ENUM('M', 'F'),
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS posts(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    post_date DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) 
    REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS companies (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(128) NOT NULL,
`role` VARCHAR(128) NOT NULL,
`start` DATE NOT NULL,
`end` DATE,
user_id INT NOT NULL,
FOREIGN KEY(user_id)
REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS movies (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    duration VARCHAR(50) NOT NULL,
    color VARCHAR(20) NOT NULL,
    file VARCHAR(255) NOT NULL,
    company_id INT NOT NULL,
    FOREIGN KEY(company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS tickets (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start DATE NOT NULL,
    start_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    half_price_ticket BOOLEAN NOT NULL DEFAULT FALSE,
    movie_id INT NOT NULL,
    FOREIGN KEY(movie_id) REFERENCES movies(id)
);

INSERT INTO users VALUES
(DEFAULT, "Emerson Carvalho", "M", "emerson@mail.com","pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="),
(DEFAULT, "Luiza Carvalho", "F", "lu@mail.com", "pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="),
(DEFAULT, "Elenice Carvalho", "F", "le@mail.com","pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="),
(DEFAULT, "Noé Carvalho", "M", "noe@mail.com","pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="),
(DEFAULT, "Rosânia Carvalho", "F", "ro@mail.com","pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="),
(DEFAULT, "Jose Carlos Souza", "M", "jose@gmail.com","pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=");

INSERT INTO posts VALUES
(DEFAULT, "Olá do Emerson", CURDATE(), 1),
(DEFAULT, "Olá da Luiza", CURDATE(), 2),
(DEFAULT, "Olá da Denise", CURDATE(), 3),
(DEFAULT, "Olá do Noé", CURDATE(), 4),
(DEFAULT, "Olá da Rosânia", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 1", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 2", CURDATE(), 5),
(DEFAULT, "Olá da Rosânia 3", CURDATE(), 5);

INSERT INTO companies (name, role, start, end, user_id) VALUES 
('Warner Bross Brasil', 'Engenheiro de Software', '2021-03-01', '2023-08-15', 1),
('Fox', 'Desenvolvedor', '2023-09-01', '2024-01-15', 2),
('Marvel', 'Arquiteto de Soluções Cloud', '2020-01-15', '2022-05-30', 3),
('Hollywood', 'Analista de Sistemas Sênior', '2022-06-01', '2024-02-28', 4),
('Youtube Films', 'Consultor de TI', '2019-02-10', '2021-02-20', 5);

INSERT INTO movies (title, duration, color, file, company_id) VALUES
('Batman: O Cavaleiro das Trevas', '152', '#000000', 'batman.mp4', 1),
('Avatar: O Caminho da Água', '192', '#0077be', 'avatar2.mp4', 2),
('Vingadores: Ultimato', '181', '#8b0000', 'endgame.mp4', 3),
('Interestelar', '169', '#191970', 'interstellar.mp4', 4),
('Oppenheimer', '180', '#ff8c00', 'oppenheimer.mp4', 5);

INSERT INTO tickets (start, start_time, price, half_price_ticket, movie_id) VALUES
('2026-11-20', '19:30:00', 42.00, FALSE, 1),
('2026-11-20', '21:00:00', 21.00, TRUE, 1),
('2026-11-21', '16:00:00', 38.00, FALSE, 2),
('2026-11-21', '20:15:00', 24.50, TRUE, 3),
('2026-11-22', '18:45:00', 45.00, FALSE, 4),
('2026-11-22', '21:30:00', 22.50, TRUE, 5);
