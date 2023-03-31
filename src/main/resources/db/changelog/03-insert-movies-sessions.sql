INSERT INTO movies (name, description, duration)
VALUES ('The Shawshank Redemption',
        'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',
        142),
       ('The Godfather',
        'An organized crime dynasty aging patriarch transfers control of his clandestine empire to his reluctant son.',
        175),
       ('The Dark Knight',
        'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.',
        152);

INSERT INTO movie_genres (movie_id, genre)
VALUES (1, 'DRAMA'),
       (1, 'CRIME'),
       (2, 'DRAMA'),
       (2, 'ACTION'),
       (3, 'ACTION'),
       (2, 'CRIME');


INSERT INTO sessions (start_time, end_time, session_type, price, hall_id, movie_id)
VALUES ('2023-03-01 10:00:00', '2023-03-01 12:22:00', '3D', 12.50, 1, 1),
       ('2023-03-01 12:30:00', '2023-03-01 14:52:00', '3D', 15.00, 1, 1),
       ('2023-03-01 15:00:00', '2023-03-01 17:17:00', '3D', 15.00, 1, 2),
       ('2023-03-01 20:00:00', '2023-03-01 22:32:00', '3D', 19.50, 1, 3),
       ('2023-03-01 12:30:00', '2023-03-01 14:52:00', '2D', 10.00, 2, 1),
       ('2023-03-01 15:00:00', '2023-03-01 17:17:00', '3D', 15.00, 3, 2),
       ('2023-03-01 17:30:00', '2023-03-01 19:25:00', '2D', 8.50, 1, 3),
       ('2023-03-01 20:00:00', '2023-03-01 22:32:00', '3D', 13.50, 2, 3);
