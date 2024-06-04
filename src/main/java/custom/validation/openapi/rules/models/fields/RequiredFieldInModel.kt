package custom.validation.openapi.rules.models.fields

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что обязательные поля для модели перечисленные в required, присутствуют в модели
 */
class RequiredFieldInModel {
    fun checkRequiredFieldInModel(openAPI: OpenAPI): List<String> {
        val missingRequiredFields = mutableListOf<String>()

        openAPI.components.schemas.forEach { (modelName, model) ->
            // Собрать все свойства, включая allOf
            val properties = model.properties?.toMutableMap() ?: mutableMapOf()
            model.allOf?.forEach { schema ->
                schema?.let {
                    if (it.`$ref` != null) {
                        val refModelName = it.`$ref`.substringAfterLast("/")
                        openAPI.components.schemas[refModelName]?.properties?.let { refProperties ->
                            properties.putAll(refProperties)
                        }
                    } else {
                        it.properties?.let { inlineProperties ->
                            properties.putAll(inlineProperties)
                        }
                    }
                }
            }

            // Проверить наличие обязательных полей
            model.required?.forEach { requiredFieldName ->
                if (!properties.containsKey(requiredFieldName)) {
                    missingRequiredFields.add(
                        "В модели \"$modelName\" отсутствует обязательное поле \"$requiredFieldName\""
                    )
                }
            }
        }

        return missingRequiredFields
    }
}