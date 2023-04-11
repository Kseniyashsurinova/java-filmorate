# java-filmorate

## Схема базы данных и примеры запросов

![Схема БЖ](Untitled.png)

1. Созданы таблиы users, films, genre и rating;
2. Тип связи между пользователями -
   "Многие к многим". Для этого создана
   таблица friends;
3. Тип связи между пользователями
   и фильмами - "Многие к многим". Для этого
   создана таблица likes;
4. Тип связи между фильмами и жанрами -
   "Многие к многим". Для этого создана
   таблица films_genre;
5. Тип связи между рейтингом и фильмом -
   "Один к многим".

### Примеры запросов пользователя

- Получение списка всех пользователей

```roomsql
SELECT *
FROM users;
```

- Получение пользователя по id

```roomsql
SELECT *
FROM users
WHERE user_id = 1
```

- Доавление в друзья

```roomsql
INSERT IGNORE INTO friends(user_id, friend_id)
VALUES (1, 2)
```

- Удаление из друзей

```roomsql
DELETE
FROM friends
WHERE user_id = 1 AND friend_id = 2
```

Список друзей пользователей

```roomsql
SELECT users.*
FROM users
INNER JOIN friends ON users.user_id = friends.friend_id
WHERE friends.user_id = 2
```

### Примеры запросов фильмов

- Получение списка всех фильмов

```roomsql
SELECT *
FROM films;
```

пользователь лайкает фильм

```roomsql
INSERT IGNORE INTO likes (film_id, user_id)
VALUES (1, 3)
```

Пользователь удаляет лайк

```roomsql
DELETE
FROM likes
WHERE film_id = 1 AND user_id = 3
```

Рейтинг фильмов по лайкам

```roomsql
SELECT films.*, rating.mpa_name, COUNT(дikes.user_id) AS rate
FROM films
         LEFT JOIN rating ON films.mpa_id = rating.mpa_id
         LEFT JOIN дikes ON films.film_id = дikes.film_id
GROUP BY films.film_id
ORDER BY rate DESC, films.film_id
LIMIT ?
```

Получить список всех жанров

```roomsql
SELECT *
FROM genres
ORDER BY genre_id
```

Получить жанр по id

```roomsql
SELECT *
FROM genres
WHERE genre_id = 1
```

Список рейтингов

```roomsql
SELECT *
FROM rating
ORDER BY mpa_id
```

Получить рейтинг по id

```roomsql
SELECT *
FROM rating
WHERE mpa_id = 1
```