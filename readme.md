# University REST Demo

REST API приложение на Spring Boot для управления студентами и группами. Проект использует MySQL для хранения данных и Liquibase для управления схемой БД.

## Запуск

Приложение запускается только через Docker Compose. Убедитесь, что у вас установлены Docker и Docker Compose.

### Первый запуск

1. Скопируйте пример конфигурации:
   ```bash
   cp .env.example .env
   ```

2. При необходимости отредактируйте `.env` файл с нужными значениями (пароли, порты и т.д.)

3. Запустите приложение:
   ```bash
   docker compose up --build
   ```

4. Приложение будет доступно по адресу `http://localhost:8080`

## Конфигурация через .env

Все настройки приложения задаются через `.env` файл. Docker Compose автоматически подхватывает переменные из этого файла и передаёт их в контейнеры.

**Основные переменные:**

- `MYSQL_ROOT_PASSWORD` - пароль root пользователя MySQL
- `MYSQL_DATABASE` - название базы данных (по умолчанию `university`)
- `SPRING_DATASOURCE_USERNAME` - пользователь для подключения к БД
- `SPRING_DATASOURCE_PASSWORD` - пароль для подключения к БД
- `SERVER_PORT` - порт приложения (по умолчанию `8080`)

**Пример `.env` файла:**
```env
MYSQL_ROOT_PASSWORD=mysecretpassword
MYSQL_DATABASE=university
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=mysecretpassword
SERVER_PORT=8080
```

## Как это работает

Проект состоит из двух контейнеров:

1. **MySQL** - база данных (порт 3307)
2. **Spring Boot приложение** - REST API (порт 8080)

При запуске Docker Compose:
- Автоматически создаётся сеть для взаимодействия контейнеров
- Spring приложение ожидает, пока MySQL будет готова
- Liquibase автоматически запускает миграции БД
- Приложение становится доступным по `http://localhost:8080`

## Структура проекта

```
demo/
├── src/
│   ├── main/
│   │   ├── java/university/task/demo/
│   │   │   ├── controller/        # REST контроллеры
│   │   │   ├── model/             # Entity классы
│   │   │   ├── repository/        # Репозитории
│   │   │   ├── service/           # Бизнес-логика
│   │   │   └── DemoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/changelog/      # Liquibase миграции
│   └── test/                      # Тесты
├── docker-compose.yaml
├── Dockerfile
├── .env.example
├── pom.xml
└── README.md
```
