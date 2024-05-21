## Пример использования
1. Подключить к проекту:
```
apply plugin: 'validation-openapi-specs'
```
2. Задать конфигурации:
```
specPath - относительный путь где необходимо искать openapi контракты


validator {
    specPath = "src/main/resources"
}
```

### Правила для валидации openapi контрактов
#### В методах:
1. Проверка, что `operationId` в методах, написан в стиле **camelCase** = `[operationId-name-style]`
2. Првоерка, что `operationId` присутствует в методах = `[operationId-present]`
3. Проверка, что `description` присутствует для каждого метода = `[description-method]`
4. Проверка, что `summary` присутствует для каждого метода = `[summary-method]`
5. Проверка, что в методах учтены все обязательные `responses codes: [20x, 40x, 50x]` = `[basic-response-code]`
6. Проверка, что в методах в поле `parameters` значение начинается с маленькой буквы = `[parameters-name]`
7. Проверка, что имя `ENUM` в параметрах прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо = `[enum-name-parameter]`
#### В моделях:
1. Проверка, что для моделей присутствует поле `Description` = `[description-model]`
2. Проверка, что для параметров в модели присутствует поле `Description` = `[description-parameter-model]`
3. Проверка, что имя модели начинается с заглавной буквы = `[model-name]`
4. Проверка, что обязательные поля для модели перечисленные в `required`, присутствуют в модели = `[required-parameter-model]`
5. Проверка, что имя `ENUM` в модели прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо = `[enum-name-model]`

### Исключение правил для контракта
#### Имена правил методов:
- operationId-name-style
- operationId-present
- description-method
- summary-method
- basic-response-code
- parameters-name
- enum-name-parameter

#### Имена правил моделей:
- description-model
- description-parameter-model
- model-name
- required-parameter-model
- enum-name-model

```
ignore - конфигурация исключений, параметр необязательный.
        - имя спецификации
        - правило которое необходимо исключить
        
validator {
    specPath = "src/main/resources"
    ignore = [
            "ump-bdui": [
                    "basic-response-code",
                     "operationId-present"
            ]
    ]
}

```
### Результат выполнения валидатора
1. Результат выводится в консоль
2. Результат сохраняется в файл `validation_errors.txt` по пути `projectDir.absolutePath + /build + /validator`