package custom.validation.openapi.rules.methods.path

import io.swagger.v3.oas.models.OpenAPI

/**
 * 1. Проверка наличия path параметров в параметрах описания операции
 * 2. Проверка параметров описаных в операции, которые не являются path параметрами
 */
class CheckPathParameters {
    fun checkPathParameters(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()
        val pathVariablePattern = Regex("\\{([^}]*)\\}")

        openAPI.paths?.forEach { (path, pathItem) ->
            // Найдем все переменные пути
            val pathVariables = pathVariablePattern.findAll(path).map { it.groupValues[1] }.toSet()

            // Проверяем параметры метода для каждой операции (GET, POST, etc.)
            pathItem.readOperationsMap().forEach { (operationMethod, operation) ->
                // Собираем параметры из секции parameters
                val declaredParameters = operation.parameters
                    ?.filter { it.`in` == "path" }
                    ?.map { it.name }
                    ?.toSet() ?: emptySet()

                // Проверка: если есть переменные в пути, но нет параметра в описании операции
                pathVariables.forEach { variable ->
                    if (!declaredParameters.contains(variable)) {
                        issues.add("В пути \"$path\" используется переменная \"$variable\", но она не объявлена в параметрах метода ${operationMethod.name}.")
                    }
                }

                // Проверка: если есть лишние параметры, которые не используются в пути
                declaredParameters.forEach { param ->
                    if (!pathVariables.contains(param)) {
                        issues.add("В методе ${operationMethod.name} для пути \"$path\" объявлен параметр \"$param\", который не используется в пути.")
                    }
                }
            }
        }

        return issues
    }

}