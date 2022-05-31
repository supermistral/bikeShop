# BikeShop
BikeShop - концепция интернет-магазина, осуществляющего продажу велосипедов и товаров тематики, связанной с ними.

## Технологии
- Java Spring Boot
- Spring Web MVC, Spring Data JPA, Spring Security
- PostgreSQL
- React.js, Node.js, Webpack 5
- Material-UI 5

## Особенности
- Понятный и унифицированный пользовательский интерфейс
- Наличие всех необходимых для поиска товара аспектов
- Разделение прав доступа на категории пользователей: потребителей, менеджеров и администраторов
- Система авторизации и аутентификации посредоством JWT-токенов (access + refresh)
- Простая и легкорасширяемая административная панель с поддержкой выполнения CRUD-операций

## Работа с приложением

### Установка
1. Склонировать репозиторий: `git clone https://github.com/supermistral/bikeShop.git`
2. Запустить проект при помощи Docker Compose: `docker-compose up`

Приложение собирает компоненты React и переносит файлы HTML, CSS, JS в раздел статических ресурсов Spring.
После этого Gradle создает jar-архив приложения Spring.

### Спецификация
`localhost:8080` - Доступ к приложению <br>
`localhost:8080/api` - Доступ к запросам API

Предустановленные учетные записи:
- _user@mail.ru_ | _useruser_ - обычный пользователь
- _manager@mail.ru_ | _manager_ - менеджер 
- _admin@mail.ru_ | _adminadmin_ - администратор

## Скриншоты
![][1]
![][2]
![][3]
![][4]
![][5]
![][6]
![][7]


[1]: screenshots/1.png
[2]: screenshots/2.png
[3]: screenshots/3.png
[4]: screenshots/4.png
[5]: screenshots/5.png
[6]: screenshots/6.png
[7]: screenshots/7.png