

### Filmorate Database ER-diagram


![ER-DIAGRAM](https://github.com/GSBoldyrev/java-filmorate/blob/main/QuickDBD-export%20(1).png)

---

## Комментарий P2P

*Привет.*

*SQL - не регистрозависимый язык. Операторы (SELECT, UPDATE и т.д.) и названия полей можно написать как в UpperCase, так и в LowerCase.* <br>
*В связи с этим плохая практика называть поля без разделения слов с помощью символа нижнего подчеркивания. Потому что поле NumberOfLikes в СУБД будет выводиться как NUMBEROFLIKES. Не очень понятно =)*

*Кстати, касательно этого поля - его следует убрать вовсе. Подсчет лайков должен осуществляться всегда заново, для этого и нужна таблица MOVIE_LIKES.*


```SQL
SELECT COUNT(1) -- Общепринятое значение для подсчета всех строк ~ COUNT(*)
FROM movie_likes
WHERE movie_id = { ID фильма }
GROUP BY movie_id -- В данном случае опционально, потому что выбирается один конкретный фильм.
```

*Для Rating_MPA предлагаю создать отдельную таблицу, где в качестве ключа будет символ(ы) рейтинга, а также их описание (поле description).*<br>
*Таким образом, если какой-либо рейтинг перестанет существовать (применяться) достаточно будет удалить его только из одной таблицы, а там, где он используется - будет проставлен NULL (при условии ограничения `ON DELETE SET NULL`).*

---


 
## Комментарий пост-ревью
- [x] Поля переименованы с использованием символа нижнего подчеркивания для улучшения читаемости запросов
- [x] Удалено поле Number_of_likes за ненадобностью
- [x] Создана отдельная таблица для рейтинга MPA. Теперь будет легче обновить данные в базе в случае изменений в рейтингах
- [x] Поправлены примеры запросов в соответствии с исправлениями

---

### Примеры запросов:

### 1. Вывод фильма

SELECT *   
FROM movie  
WHERE movie_ID = 1;  

### 2. Вывод пользователя

SELECT *  
FROM user  
WHERE user_ID = 1;  

### 3. Вывод пользователей, лайкнувших фильм

SELECT u.name,  
       u.login,  
       u.email  
FROM movie_likes AS m  
LEFT OUTER JOIN user AS u ON m.user_ID = u.user_ID  
WHERE m.movie_ID = 1;   

### 4. Вывод списка друзей

SELECT u.name,  
       u.login,  
       u.email  
FROM friends AS f  
LEFT OUTER JOIN user AS u ON f.friend_ID = u.user_ID  
WHERE f.user_ID = 1;   

### 5. Вывод статуса заявки в друзья

SELECT approval  
FROM friends   
WHERE user_ID = 1 AND friend_ID = 2;  
