###### Итоговая аттестация
###Ключевые технологии и подходы:
##1. Модульное тестирование:
#_JUnit 5 (Jupiter)_
основной инструмент для проведения модульных тестов. Позволяет создавать четкую структуру тестов, обеспечивать подготовку и очистку данных перед и после каждого теста.
#_Rest Assured_
фреймворк для тестирования RESTful-сервисов. Упрощает построение и проверку HTTP-запросов, обеспечивая удобство и надежность тестирования API.
#_Hamcrest и AssertJ_
библиотеки для создания мощных и выразительных утверждений, что делает тесты более читабельными и надежными.
##2. Интеграционное тестирование:
#_Selenium WebDriver_  
инструмент для автоматизации браузера, позволяющий проводить сложные сценарии тестирования UI. Поддерживает работу с различными браузерами, такими как Chrome, Firefox и др.
#_Page Object Model (POM)_ 
паттерн проектирования, который позволяет отделять логику взаимодействия с элементами страницы от самих тестов, делая код более структурированным и поддерживаемым.
##3. Работа с базами данных:
#_JDBC (Java Database Connectivity)_ 
стандартное API для взаимодействия с базами данных из Java. В нашем проекте используется для проверки данных в базе данных PostgreSQL.
##4. Отчеты и документация:
#_Allure Framework_
мощный инструмент для создания красивых и информативных отчетов. Позволяет добавлять метаданные к тестам, такие как эпики, истории, ссылки на требования, а также пошагово описывать выполнение тестов.

###### Инструкция по запуску автотестов:
1. Клонировать проект используюя команду `git clone` https://github.com/EroslaevaAnna/xClients.git
2. В терминале перейти в директорию xClients командой `cd` xClients
3. Выполнить команду `mvn clean test`
###### Инструкция по запуску Allure отчета 
1. После запуска тестов перейти в терминал 
2. В терминае перейти в директорию test командой `cd` test
3. Выполнить команду `allure serve`

