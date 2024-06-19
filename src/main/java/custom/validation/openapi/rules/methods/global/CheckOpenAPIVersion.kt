package custom.validation.openapi.rules.methods.global

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что версия OpenApi имеет значение 3.0.1
 */
class CheckOpenAPIVersion {

    fun checkOpenAPIVersion(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        val openAPIVersion = openAPI.openapi
        if (openAPIVersion != "3.0.1") {
            issues.add("Поле openapi имеет значение \"$openAPIVersion\", ожидалось \"3.0.1\".")
        }

        return issues
    }
}