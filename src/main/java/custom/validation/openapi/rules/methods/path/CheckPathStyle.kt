package custom.validation.openapi.rules.methods.path

import io.swagger.v3.oas.models.OpenAPI

/**
 * Проверка, что путь не содержит символ _ и не написан в стиле camelCase
 */
class CheckPathStyle {

    fun checkPathStyle(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()
        val camelCasePattern = Regex(".*[A-Z].*")
        val underscorePattern = Regex(".*_.*")
        val pathVariablePattern = Regex("\\{[^}]*\\}")

        openAPI.paths?.forEach { (path, _) ->
            path.split('/').forEach { segment ->
                if (!segment.matches(pathVariablePattern)) {
                    if (segment.matches(camelCasePattern)) {
                        issues.add("Путь \"$path\" содержит сегмент \"$segment\" в стиле camelCase.")
                    }
                    if (segment.matches(underscorePattern)) {
                        issues.add("Путь \"$path\" содержит сегмент \"$segment\" с символом underscore (_).")
                    }
                }
            }
        }
        return issues
    }
}