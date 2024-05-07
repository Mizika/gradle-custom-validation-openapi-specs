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
    fun combinedCheckMethods(openAPI: OpenAPI): MutableList<String> {
        val errorInMethods: MutableList<String> = ArrayList()

        val operationIdCamelCase = OperationIdCamelCase().checkOperationIdCamelCase(openAPI)
        errorInMethods.addAll(operationIdCamelCase!!)

        val operationIdNotEmpty = OperationIdIsNotEmpty().checkOperationIdIsNotEmpty(openAPI)
        errorInMethods.addAll(operationIdNotEmpty!!)

        val descriptionNotEmpty = DescriptionForMethodIsNotEmpty().checkDescriptionIsNotEmpty(openAPI)
        errorInMethods.addAll(descriptionNotEmpty!!)

        val summaryNotEmpty = SummaryForMethodIsNotEmpty().checkSummaryIsNotEmpty(openAPI)
        errorInMethods.addAll(summaryNotEmpty!!)

        val basicResponseCode = BasicResponseCode().checkBasicResponseCode(openAPI)
        errorInMethods.addAll(basicResponseCode)

        val formatParameters = FormatNameOfParameters().checkFormatNameOfParameters(openAPI)
        errorInMethods.addAll(formatParameters!!)

        return errorInMethods
    }
}