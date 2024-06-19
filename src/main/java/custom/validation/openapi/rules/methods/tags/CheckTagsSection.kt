package custom.validation.openapi.rules.methods.tags

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что секция tags существует и не пустая
 */
class CheckTagsSection {

    fun checkTagsSection(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        val tags = openAPI.tags
        if (tags == null || tags.isEmpty()) {
            issues.add("Секция tags отсутствует или пуста.")
        }

        return issues
    }
}