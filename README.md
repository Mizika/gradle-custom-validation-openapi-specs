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
8. Проверка, что теги как глобальные так и в методах написаны на английском языке = `[tags-name]`
9. Проверка, что в каждом методе указан тэг = `[tags-operation]`
10. Проверка, что в методах присутствуют только те тэги, которые определены в секции tags спецификации OpenAPI = `[tags-list-operation]`
11. Проверка, что для каждого тэга на уровне компонентов тэгов есть описание = `[tags-description]`
12. Проверка, что секция tags существует и не пустая = `[tags-section]`
13. Проверка, что в секции info есть поле version, которое не пустое и соответствует паттерну SemVer (Semantic Versioning) = `[global-version]`
14. Проверка, что версия OpenApi имеет значение 3.0.1 = `[global-openapi-version]`
15. Проверка, что путь не содержит символ `_` и не написан в стиле `camelCase` = `[check-path-style]`
16. Проверка path параметров = `[check-path-parameters]`
17. Првоерка наличия тела в методах, которые не должны содержать тело = `[check-body-in-method]`
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
- tags-name
- tags-operation
- tags-list-operation
- tags-description
- tags-section
- global-version
- global-openapi-version
- check-path-style
- check-path-parameters
- check-body-in-method

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