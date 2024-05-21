package custom.validation.openapi.rules.models

import custom.validation.openapi.rules.models.descriptions.ModelDescription
import custom.validation.openapi.rules.models.descriptions.ModelPropertiesDescription
import custom.validation.openapi.rules.models.fields.RequiredFieldInModel
import custom.validation.openapi.rules.models.name.ModelEnumName
import custom.validation.openapi.rules.models.name.ModelName
import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс с объединенными правилами для проверки моделей
 */
class CombinedCheckModels {

    fun combinedCheckModels(openAPI: OpenAPI, ignore: List<String>): List<String> {
        val errorInModels = mutableListOf<String>()

        val modelChecks = mapOf(
            "description-model" to ModelDescription()::checkModelDescription,
            "description-parameter-model" to ModelPropertiesDescription()::checkModelPropertiesDescription,
            "model-name" to ModelName()::checkModelName,
            "required-parameter-model" to RequiredFieldInModel()::checkRequiredFieldInModel,
            "enum-name-model" to ModelEnumName()::checkEnumNamesInModel
        )

        for ((checkName, checkMethod) in modelChecks) {
            if (!ignore.contains(checkName)) {
                val errors = checkMethod(openAPI)
                errors?.let { errorInModels.addAll(it) }
            }
        }
        return errorInModels
    }
}