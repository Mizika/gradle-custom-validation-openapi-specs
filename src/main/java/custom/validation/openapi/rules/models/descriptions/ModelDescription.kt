package custom.validation.openapi.rules.models.descriptions

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что для моделей присутствует поле Description и не имеет значение null
 */
class ModelDescription {
    fun checkModelDescription(openAPI: OpenAPI): List<String>? {
        val checkModelDescription: MutableList<String> = ArrayList()
        for (modelName: String in openAPI.components.schemas.keys) {
            try {
                if (openAPI.components.schemas[modelName]!!.description == "null" ||
                    openAPI.components.schemas[modelName]!!.description.length <= 2
                ) {
                    checkModelDescription.add(
                        "Для модели \"$modelName\" пустое поле description"
                    )
                }
            } catch (e: Exception) {
                checkModelDescription.add(
                    "Для модели \"$modelName\" отсутствует поле description"
                )
            }
        }
        return checkModelDescription
    }
}