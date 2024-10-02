package custom.validation.openapi.rules.models.enumcheck

import io.swagger.v3.oas.models.OpenAPI

/**
 * Проверка на дубликаты значений ENUM
 */
class CheckEnumDuplicates {
    fun checkEnumDuplicates(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        openAPI.components.schemas.forEach { (modelName, schema) ->
            schema.enum?.let { enumValues ->
                // Группируем значения enum и считаем количество повторений
                val duplicates = enumValues.groupingBy { it }.eachCount().filter { it.value > 1 }

                if (duplicates.isNotEmpty()) {
                    duplicates.forEach { (enumValue, count) ->
                        issues.add("В модели \"$modelName\" значение ENUM \"$enumValue\" повторяется $count раз.")
                    }
                }
            }

            // Проверка enum в свойствах модели
            schema.properties?.forEach { (propertyName, propertySchema) ->
                propertySchema.enum?.let { enumValues ->
                    // Группируем значения enum и считаем количество повторений
                    val duplicates = enumValues.groupingBy { it }.eachCount().filter { it.value > 1 }

                    if (duplicates.isNotEmpty()) {
                        duplicates.forEach { (enumValue, count) ->
                            issues.add("В модели \"$modelName\" в свойстве \"$propertyName\" значение ENUM \"$enumValue\" повторяется $count раз.")
                        }
                    }
                }
            }
        }
        return issues
    }

}