package custom.validation.openapi.rules.models.descriptions

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что для параметров в модели присутствует поле Description и не имеет значение null.
 */
class ModelPropertiesDescription {
    fun checkModelPropertiesDescription(openAPI: OpenAPI): List<String> {
        val checkModelPropertiesDescription: MutableList<String> = mutableListOf()
        openAPI.components?.schemas?.forEach { (modelName, model) ->
            model.properties?.forEach { (propertyName, property) ->
                val description = property.description
                if (description == null || description.length <= 2) {
                    val message = if (description == null)
                        "отсутствует поле description"
                    else
                        "пустое поле description"

                    checkModelPropertiesDescription.add(
                        "Для модели \"$modelName\" в параметре \"$propertyName\" $message"
                    )
                }
            }
        }
        return checkModelPropertiesDescription
    }
}