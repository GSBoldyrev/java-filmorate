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
