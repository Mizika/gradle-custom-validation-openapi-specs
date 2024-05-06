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
#### В методах
1. Проверка, что `operationId` в методах, написан в стиле **camelCase**
2. Првоерка, что `operationId` присутсвтует в методах 