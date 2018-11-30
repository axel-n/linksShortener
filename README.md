# Тестовый проект (будущий сокращатель ссылок)

## Используется
- spring
- react.js
- h2

# Что умееет
- некоторое api
    - показывать все данные /api/employees/
    - показывать только 1 элемент /api/employees/1
    - добавление данных
    ```
    curl -X POST localhost:8080/api/employees -d "{\"firstName\": \"Bilbo\", \"lastName\": \"Baggins\", \"description\": \"burglar\"}" -H "Content-Type:application/json"
    ```

- некоторый UI
    - показывает главную со всеми данными в виде таблицы
