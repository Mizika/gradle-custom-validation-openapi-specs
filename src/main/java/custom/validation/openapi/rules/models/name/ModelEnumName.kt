package custom.validation.openapi.rules.models.name

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что имя енама в модели прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо
 */
class ModelEnumName {
    fun checkModelEnumName(openAPI: OpenAPI): List<String> {
        val checkEnumName: MutableList<String> = mutableListOf()
        val camelCasePattern = Regex("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$")
        openAPI.components.schemas.forEach { (modelName, model) ->
            model.enum?.forEach { enumName ->
                if (!enumName.toString().matches(camelCasePattern)) {
                    checkEnumName.add(
                        "Некорректный формат ENUM \"$enumName\" в модели \"$modelName\"!"
                    )
                }
            }
        }
        return checkEnumName
    }
}