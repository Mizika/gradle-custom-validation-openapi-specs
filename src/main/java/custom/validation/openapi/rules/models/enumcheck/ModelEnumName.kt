package custom.validation.openapi.rules.models.enumcheck

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что имя енама в модели прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо
 */
class ModelEnumName {

    fun checkEnumNamesInModel(openAPI: OpenAPI): List<String> {
        val checkEnumName = mutableListOf<String>()
        val camelCasePattern = Regex("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$")

        openAPI.components.schemas.forEach { (modelName, schema) ->
            schema.enum?.forEach { enumValue ->
                if (!enumValue.toString().matches(camelCasePattern)) {
                    checkEnumName.add("Некорректный формат ENUM \"$enumValue\" в модели \"$modelName\"!")
                }
            }
            schema.properties?.forEach { (propertyName, propertySchema) ->
                propertySchema?.enum?.forEach { enumValue ->
                    if (!enumValue.toString().matches(camelCasePattern)) {
                        checkEnumName.add(
                            "Некорректный формат ENUM \"$enumValue\" в свойстве \"$propertyName\" модели \"$modelName\"!"
                        )
                    }
                }
            }
        }

        return checkEnumName
    }

}