package custom.validation.openapi.rules.methods.tags

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, описаний на уровне компонентов тэгов
 */
class CheckTagsDescription {

    fun checkTagsDescription(openAPI: OpenAPI): List<String> {
        val missingTagDescriptions: MutableList<String> = mutableListOf()

        openAPI.tags?.forEach { tag ->
            if (tag.description.isNullOrEmpty()) {
                missingTagDescriptions.add("Для тэга \"${tag.name}\" отсутствует описание.")
            }
        }
        return missingTagDescriptions
    }
}