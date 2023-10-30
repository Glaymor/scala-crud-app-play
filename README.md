# scala-crud-app-play

REST API приложение в стиле CRUD.
Приложения для хранения, изменения списка фильмов. 
Технологии:
Play Framework
Slick
Postres

О приложении:

Стартовая страница

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/5862495a-31b8-415e-a371-4e804ed022be)

Структура: 

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/97d78514-8be8-4c90-ab3b-16c43b535636)


Routes файл сопоставления запросов методам контроллера

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/dd58f76b-48c7-4fd3-ae30-edf4bf5a74a7)

Контроллер 

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/4d5c14b1-b603-434b-bf7a-c56bcb76a07f)

При первом обращении вызывается метод info() который отправляет HTML страницу с информацией о приложении.

Сервис для вызова методов в репозитории

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/c62a067b-7db0-47ff-82f3-ccd10024a81f)

Репозиторий содержит структура базы данных и методы получения, изменения и удаления фильмов

Структура: 
![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/f40099fe-83f6-4ef7-a9a3-3471d34fc6da)

Методы 

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/5dcbf4b4-5ded-4ab0-bde7-2fea7db5a22c)

Добавление нового элемента

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/3ff026f3-94ee-416e-83ab-55c07ec9d563)

Получение всех элементов

![image](https://github.com/Glaymor/scala-crud-app-play/assets/91942559/a78b7326-8c24-4ae2-8355-31d896f67be4)




