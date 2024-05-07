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
    fun combinedCheckModels(openAPI: OpenAPI): MutableList<String> {
        val errorInModels: MutableList<String> = ArrayList()

        val modelDescriptions = ModelDescription().checkModelDescription(openAPI)
        errorInModels.addAll(modelDescriptions!!)

        val modelPropertiesDescription = ModelPropertiesDescription().checkModelPropertiesDescription(openAPI)
        errorInModels.addAll(modelPropertiesDescription)

        val modelName = ModelName().checkModelName(openAPI)
        errorInModels.addAll(modelName!!)

        val requiredFieldInModel = RequiredFieldInModel().checkRequiredFieldInModel(openAPI)
        errorInModels.addAll(requiredFieldInModel)

        val modelEnumName = ModelEnumName().checkModelEnumName(openAPI)
        errorInModels.addAll(modelEnumName)

        return errorInModels
    }
}