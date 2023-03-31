INSERT INTO users (name, surname, email, password)
VALUES ('Ali', 'Aliyev', 'ali@mail.com', 'ali123'),
       ('Vali', 'Valiyev', 'vali@mail.com', 'vali123'),
       ('Bob', 'Smith', 'bob@mail.com', 'password');

INSERT INTO refresh_token (string_token, created_date, updated_date, user_id)
VALUES ('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoYWxpQGdtYWlsLmNvbSIsImlhdCI6MTY4MDE4MzI4OCwiZXhwIjoxNjgyNzc1Mjg4fQ.7HkIamR-Ia6wcG6-ovbaFJHP7lmvBBy0rIYT24dt_A7xphKMkR-w4-lK2_e9pt0Rn48ektSHd_lq2OFbbFkvfA',
        '2023-03-30 00:00:00', '2023-03-30 00:00:00', 1),
       ('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2YWxpQGdtYWlsLmNvbSIsImlhdCI6MTY4MDIwMTM3OCwiZXhwIjoxNjgwMjg3Nzc4fQ.NLWjeNERy7qzqitxdxnU9vGgEox3QBMSt4s55Hy7fH6haEjWTBBd0h4fHWZfLawiTUdPN5z78lqVMHZ7r7nqoQ',
        '2023-03-30 00:00:00', '2023-03-30 00:00:00', 2),
       ('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNodmFsaUBnbWFpbC5jb20iLCJpYXQiOjE2ODAyMDEzNjUsImV4cCI6MTY4Mjc5MzM2NX0.Avnd1aMGpRFe9fjJYcEAur1ntXM17CZ71qgi-ZzbDSziyjU1kT_6m5H2xPdjzWcPKbZqxUqU17BRzipG9WE2ZA',
        '2023-03-30 00:00:00', '2023-03-30 00:00:00', 3);

INSERT INTO tickets (user_id, session_id, seat_id)
VALUES (1, 1, 5),
       (1, 1, 6),
       (1, 1, 7),
       (3, 4, 20),
       (3, 5, 26),
       (3, 6, 34),
       (3, 7, 40),
       (1, 8, 43);
