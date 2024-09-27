package custom.validation.openapi.rules.methods.error

import io.swagger.v3.oas.models.OpenAPI

/**
 * Проверка, что метод без path параметров не содержит ошибку 404
 */
class Check404ErrorForOperationsWithoutPathParams {

    fun check404ErrorForOperationsWithoutPathParams(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        openAPI.paths?.forEach { (path, pathItem) ->
            pathItem.readOperationsMap().forEach { (operationMethod, operation) ->
                val hasPathParams = operation.parameters?.any { it.`in` == "path" } ?: false

                if (!hasPathParams) {
                    val responses = operation.responses

                    if (responses.containsKey("404")) {
                        issues.add("Метод ${operationMethod.name.toUpperCase()} " +
                                "для пути \"$path\" содержит описание ошибки 404, хотя в параметрах нет переменных.")
                    }
                }
            }
        }
        return issues
    }


}