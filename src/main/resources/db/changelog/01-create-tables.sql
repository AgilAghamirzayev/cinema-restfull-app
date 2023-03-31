CREATE TABLE users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50)  NOT NULL,
    surname  VARCHAR(50)  NOT NULL,
    email    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    balance  DECIMAL(10, 2) DEFAULT 100.00
);

CREATE TABLE refresh_token
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    string_token       VARCHAR(600) NOT NULL UNIQUE,
    created_date TIMESTAMP    NOT NULL,
    updated_date TIMESTAMP    NOT NULL,
    user_id            INT          NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE cinemas
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE movies
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    duration    INT          NOT NULL
);

CREATE TABLE movie_genres
(
    movie_id INT         NOT NULL,
    genre    VARCHAR(50) NOT NULL,
    PRIMARY KEY (movie_id, genre),
    CONSTRAINT movie_fk FOREIGN KEY (movie_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE halls
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    max_seats INT         NOT NULL,
    cinema_id INT         NOT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
);

CREATE TABLE sessions
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    start_time   TIMESTAMP      NOT NULL,
    end_time     TIMESTAMP      NOT NULL,
    session_type VARCHAR(50),
    price        DECIMAL(10, 2) NOT NULL,
    hall_id      INT            NOT NULL,
    movie_id     INT            NOT NULL,
    FOREIGN KEY (hall_id) REFERENCES halls (id),
    FOREIGN KEY (movie_id) REFERENCES movies (id)
);

CREATE TABLE seats
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    row_of_seat    INT NOT NULL,
    column_of_seat INT NOT NULL,
    seat_status    VARCHAR(50),
    hall_id        INT NOT NULL,
    FOREIGN KEY (hall_id) REFERENCES halls (id)
);

CREATE TABLE tickets
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT NOT NULL,
    session_id INT NOT NULL,
    seat_id    INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (session_id) REFERENCES sessions (id),
    FOREIGN KEY (seat_id) REFERENCES seats (id)
);
