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
2. Првоерка, что `operationId` присутсвтует в методах = `[operationId-present]`
3. Проверка, что `description` присутсвует для каждого метода = `[description-method]`
4. Проверка, что `summary` присутсвует для каждого метода = `[summary-methods]`
5. Проверка, что в методах учтены все обязательные `responses codes: [20x, 40x, 50x]` = `[basic-response-code]`
6. Проверка, что в методах в поле `parameters` значение начинается с маленькой буквы = `[parameters-name]`
#### В моделях:
1. Првоерка, что для моделей присутствует поле `Description` = `[description-model]`
2. Првоерка, что для параметров в модели присутствует поле `Description` = `[description-parametr-model]`
3. Првоерка, что имя модели начинается с заглавной буквы = `[model-name]`
4. Првоерка, что обязательные поля для модели перечисленные в `required`, присутствуют в модели = `[required-parametr-model]`
5. Првоерка, что имя `EMUN` в модели прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо = `[enum-name-model]`

### Исключение правил для контракта
#### Имена правил методов:
- operationId-name-style
- operationId-present
- description-method
- summary-methods
- basic-response-code
- parameters-name

#### Имена правил моделей:
- description-model
- description-parametr-model
- model-name
- required-parametr-model
- enum-name-model

```
config - конфигурация исклюения, параметр необязательный
        - имя спецификации
        - правило которое необходимо исключить : true/false
        
validator {
    specPath = "src/main/resources"
    config = [
            "ump-bdui": [
                    "basic-response-code": true
            ]
    ]
}

```