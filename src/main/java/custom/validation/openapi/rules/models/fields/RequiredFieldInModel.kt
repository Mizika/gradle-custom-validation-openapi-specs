package custom.validation.openapi.rules.models.fields

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что обязательные поля для модели перечисленные в required, присутствуют в модели
 */
class RequiredFieldInModel {
    fun checkRequiredFieldInModel(openAPI: OpenAPI): List<String> {
        val missingRequiredFields: MutableList<String> = mutableListOf()
        openAPI.components.schemas.forEach { (modelName, model) ->
            model.required?.forEach { requiredFieldName ->
                if (!model.properties.containsKey(requiredFieldName)) {
                    missingRequiredFields.add(
                        "В модели \"$modelName\" отсутствует обязательное поле \"$requiredFieldName\""
                    )
                }
            }
        }
        return missingRequiredFields
    }
}