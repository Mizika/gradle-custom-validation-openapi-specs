package custom.validation.openapi.rules.methods

import custom.validation.openapi.rules.methods.description.DescriptionForMethodIsNotEmpty
import custom.validation.openapi.rules.methods.errorcode.BasicResponseCode
import custom.validation.openapi.rules.methods.operationid.OperationIdCamelCase
import custom.validation.openapi.rules.methods.operationid.OperationIdIsNotEmpty
import custom.validation.openapi.rules.methods.parameters.FormatNameOfParameters
import custom.validation.openapi.rules.methods.summary.SummaryForMethodIsNotEmpty
import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс с объединенными правилами для проверки методов
 */
class CombinedCheckMethods {

    fun combinedCheckMethods(openAPI: OpenAPI, ignore: List<String>): List<String> {
        val errorInMethods = mutableListOf<String>()

        val methodChecks = mapOf(
            "operationId-name-style" to OperationIdCamelCase()::checkOperationIdCamelCase,
            "operationId-present" to OperationIdIsNotEmpty()::checkOperationIdIsNotEmpty,
            "description-method" to DescriptionForMethodIsNotEmpty()::checkDescriptionIsNotEmpty,
            "summary-method" to SummaryForMethodIsNotEmpty()::checkSummaryIsNotEmpty,
            "basic-response-code" to BasicResponseCode()::checkBasicResponseCode,
            "parameters-name" to FormatNameOfParameters()::checkFormatNameOfParameters
        )

        for ((checkName, checkMethod) in methodChecks) {
            if (!ignore.contains(checkName)) {
                val errors = checkMethod(openAPI)
                errors?.let { errorInMethods.addAll(it) }
            }
        }

        return errorInMethods
    }
}