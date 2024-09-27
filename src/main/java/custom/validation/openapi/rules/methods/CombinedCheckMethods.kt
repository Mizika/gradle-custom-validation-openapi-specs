package custom.validation.openapi.rules.methods

import custom.validation.openapi.rules.methods.body.CheckBodyInMethodsThatShouldNotContainBody
import custom.validation.openapi.rules.methods.description.DescriptionForMethodIsNotEmpty
import custom.validation.openapi.rules.methods.error.Check404ErrorForOperationsWithoutPathParams
import custom.validation.openapi.rules.methods.errorcode.BasicResponseCode
import custom.validation.openapi.rules.methods.global.CheckInfoVersion
import custom.validation.openapi.rules.methods.global.CheckOpenAPIVersion
import custom.validation.openapi.rules.methods.operationid.OperationIdCamelCase
import custom.validation.openapi.rules.methods.operationid.OperationIdIsNotEmpty
import custom.validation.openapi.rules.methods.parameters.EnumNameInParameters
import custom.validation.openapi.rules.methods.parameters.FormatNameOfParameters
import custom.validation.openapi.rules.methods.path.CheckPathParameters
import custom.validation.openapi.rules.methods.path.CheckPathStyle
import custom.validation.openapi.rules.methods.summary.SummaryForMethodIsNotEmpty
import custom.validation.openapi.rules.methods.tags.*
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
            "parameters-name" to FormatNameOfParameters()::checkFormatNameOfParameters,
            "enum-name-parameter" to EnumNameInParameters()::checkEnumNamesInParameters,
            "tags-name" to TagsName()::checkTagsName,
            "tags-description" to CheckTagsDescription()::checkTagsDescription,
            "tags-operation" to CheckOperationTags()::checkOperationTags,
            "tags-list-operation" to CheckOperationTagsValidity()::checkOperationTagsValidity,
            "tags-section" to CheckTagsSection()::checkTagsSection,
            "global-version" to CheckInfoVersion()::checkInfoVersion,
            "global-openapi-version" to CheckOpenAPIVersion()::checkOpenAPIVersion,
            "check-path-style" to CheckPathStyle()::checkPathStyle,
            "check-path-parameters" to CheckPathParameters()::checkPathParameters,
            "check-body-in-method" to CheckBodyInMethodsThatShouldNotContainBody()::checkBodyInMethodsThatShouldNotContainBody,
            "check-404-code" to Check404ErrorForOperationsWithoutPathParams()::check404ErrorForOperationsWithoutPathParams
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