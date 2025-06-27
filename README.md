# 📚 Книжный магазин - Веб-приложение

Полнофункциональное веб-приложение для управления каталогом книг, построенное на Spring Boot 3.2.3 с современным фронтендом.

## 🚀 Возможности

### Бэкенд (Spring Boot)
- ✅ **Java 17** - современная версия Java
- ✅ **Spring Boot 3.2.3** - последняя версия фреймворка
- ✅ **Spring Web** - REST API
- ✅ **Spring Security** - безопасность (временно отключена для разработки)
- ✅ **H2 Database** - встроенная база данных для тестирования
- ✅ **CRUD операции** - полный набор операций с книгами
- ✅ **Поиск** - поиск по названию и автору
- ✅ **CORS** - поддержка кросс-доменных запросов
- ✅ **AOP Logging** - логирование через аспекты

### Фронтенд
- ✅ **Современный UI** - Bootstrap 5.3.0
- ✅ **Адаптивный дизайн** - работает на всех устройствах
- ✅ **Font Awesome** - красивые иконки
- ✅ **JavaScript ES6+** - современный JavaScript
- ✅ **Fetch API** - асинхронные запросы к серверу
- ✅ **Уведомления** - Toast сообщения
- ✅ **Модальные окна** - подтверждение действий

## 🛠 Технологии

### Бэкенд
- Java 17
- Spring Boot 3.2.3
- Spring Web
- Spring Security
- Spring Data JPA
- H2 Database
- Gradle
- Lombok

### Фронтенд
- HTML5
- CSS3 (с градиентами и анимациями)
- JavaScript ES6+
- Bootstrap 5.3.0
- Font Awesome 6.0.0

## 📋 API Endpoints

### Книги
- `GET /api/books` - получить все книги
- `GET /api/books/{id}` - получить книгу по ID
- `POST /api/books` - создать новую книгу
- `PUT /api/books/{id}` - обновить книгу
- `DELETE /api/books/{id}` - удалить книгу
- `GET /api/books/search?query={query}` - поиск книг

### Веб-интерфейс
- `GET /` - главная страница приложения

## 🚀 Запуск приложения

### Предварительные требования
- Java 17 или выше
- Gradle (или используйте встроенный gradlew)

### Установка и запуск

1. **Клонируйте репозиторий**
   ```bash
   git clone <repository-url>
   cd kj
   ```

2. **Установите Java 17** (если не установлена)
   - Скачайте с [Eclipse Adoptium](https://adoptium.net/)
   - Установите JAVA_HOME

3. **Запустите приложение**
   ```bash
   # Windows
   set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.15.6-hotspot
   ./gradlew bootRun
   
   # Linux/Mac
   export JAVA_HOME=/path/to/java17
   ./gradlew bootRun
   ```

4. **Откройте браузер**
   - Перейдите на http://localhost:8080
   - Наслаждайтесь приложением!

## 📱 Использование

### Веб-интерфейс
1. Откройте http://localhost:8080 в браузере
2. Просматривайте каталог книг
3. Используйте поиск для нахождения нужных книг
4. Добавляйте новые книги через форму
5. Редактируйте и удаляйте существующие книги

### API
Используйте любой HTTP клиент (Postman, curl, etc.) для работы с API:

```bash
# Получить все книги
curl http://localhost:8080/api/books

# Добавить новую книгу
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Новая книга","author":"Автор","isbn":"123-456","price":299.99}'

# Поиск книг
curl "http://localhost:8080/api/books/search?query=Толстой"
```

## 🎨 Особенности дизайна

- **Градиентные фоны** - современный внешний вид
- **Анимации** - плавные переходы и эффекты
- **Карточки книг** - красивое отображение информации
- **Адаптивность** - работает на мобильных устройствах
- **Темная тема навигации** - контрастный дизайн
- **Иконки** - интуитивно понятный интерфейс

## 🔧 Конфигурация

### База данных
По умолчанию используется H2 in-memory база данных. Конфигурация в `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:bookstore
spring.h2.console.enabled=true
```

### Безопасность
Spring Security настроен для разрешения всех запросов (для разработки):

```java
.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
```

## 📊 Тестовые данные

Приложение автоматически создает тестовые книги:
1. "Война и мир" - Лев Толстой (599.99 ₽)
2. "Преступление и наказание" - Федор Достоевский (449.99 ₽)
3. "Мастер и Маргарита" - Михаил Булгаков (399.99 ₽)

## 🐛 Отладка

### Логи
Приложение выводит подробные логи в консоль. Уровень логирования настроен в `application.properties`.

### H2 Console
Доступна веб-консоль H2 для просмотра базы данных:
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:bookstore`
- Username: `sa`
- Password: (пустой)

## 🚀 Развертывание

### Сборка JAR
```bash
./gradlew build
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Docker (опционально)
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📝 Лицензия

Этот проект создан в учебных целях.

## 👨‍💻 Автор

Создано с использованием современных технологий Spring Boot и веб-разработки.

---

**Приятного использования! 📚✨** 