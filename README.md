# spring-telegram-qiwi-banker
Это Spring-приложение для демонстрации взаимодействия между Telegram Bot API and Qiwi API.

# Конфигурация
1. Открыть  `paybot-backend\src\main\java\com\iamnzrv\paybot\config`.
2. В файле `DatabaseConfig.java` указать свои данные для подключения к базе.
3. В файле `JwtConfig.java` указать данные для генерации токена, используемого при авторизации.
   `secretKey` может быть любой последовательностью символов больше 80.
4. В файле `WalletConfig.java` указать номер и токен своего Qiwi кошелька.