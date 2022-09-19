MERGE INTO GENRES (GENRE_ID, DESCRIPTION)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Боевик'),
           (5, 'Фантастика'),
           (6, 'Биография');

MERGE INTO MPA_RATING (MPA_ID, DESCRIPTION)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA)
    VALUES ('Хоббит', 'Про хоббитов', '2010-12-08', 200, 4, 1),
           ('Аватар', 'Про синих', '2005-12-08', 100, 5, 5),
           ('Звездный путь', 'Про космос', '2000-12-08', 200, 6, 3),
           ('Бэтмен', 'Про бандитов', '1995-12-08', 100, 7, 4),
           ('Титаник', 'Про море', '1990-12-08', 200, 8, 5);

INSERT INTO MOVIE_GENRE (FILM_ID, GENRE_ID)
    VALUES (1, 6),
           (1, 2),
           (2, 4),
           (2, 5),
           (2, 2),
           (3, 5),
           (3, 1),
           (4, 4),
           (5, 2),
           (5, 6);

INSERT INTO USERS (E_MAIL, LOGIN, NAME, BIRTHDAY)
    VALUES ('qwe@mail.ru', 'Bilbo', 'Baggins', '1990-10-11'),
           ('rty@mail.ru', 'Turuk_makto', 'Jeorge', '1990-10-11'),
           ('uio@mail.ru', 'Spok', 'Noname', '1990-10-11'),
           ('asd@mail.ru', 'Joker', 'Brus', '1990-10-11'),
           ('fgh@mail.ru', 'Rosa', 'Douson', '1990-10-11');

INSERT INTO FRIENDS (USER_ID, FRIEND_ID)
    VALUES (2, 3),
           (2, 4),
           (2, 1),
           (4, 2),
           (4, 3),
           (1, 4);

