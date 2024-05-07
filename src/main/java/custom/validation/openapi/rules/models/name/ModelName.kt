package custom.validation.openapi.rules.models.name

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что имя модели начинается с заглавной буквы
 */
class ModelName {
    fun checkModelName(openAPI: OpenAPI): List<String>? {
        val checkModelName: MutableList<String> = ArrayList()
        val camelCasePattern = "([A-Z]+[A-Za-z]+\\w+)+"
        for (modelName: String in openAPI!!.components.schemas.keys) {
            if (!modelName.matches(camelCasePattern.toRegex())) {
                checkModelName.add(
                    "Некорректный формат имени модели \"$modelName\""
                )
            }
        }
        return checkModelName
    }
}