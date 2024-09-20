package custom.validation.openapi.rules.methods.body

import io.swagger.v3.oas.models.OpenAPI

/**
 * Првоерка наличия тела в методах, которые не должны содержать тело
 */
class CheckBodyInMethodsThatShouldNotContainBody {

    fun checkBodyInMethodsThatShouldNotContainBody(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        // Массив методов, для которых не должно быть тела запроса
        val methodsWithoutRequestBody = listOf("get", "delete")

        // Перебираем все пути и методы
        openAPI.paths?.forEach { (path, pathItem) ->
            pathItem.readOperationsMap().forEach { (operationMethod, operation) ->
                // Проверяем, что метод входит в список методов, которые не должны содержать тело запроса
                if (methodsWithoutRequestBody.contains(operationMethod.name.toLowerCase())) {
                    // Проверяем наличие тела запроса (request body)
                    if (operation.requestBody != null) {
                        issues.add("Метод ${operationMethod.name.toUpperCase()} для пути \"$path\" содержит тело запроса, что не разрешено.")
                    }
                }
            }
        }
        return issues
    }
}