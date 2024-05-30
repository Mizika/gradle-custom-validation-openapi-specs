package custom.validation.openapi.rules.methods.tags

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что теги как глобальные так и в методах написаны на английском языке
 */
class TagsName {
    fun checkTagsName(openAPI: OpenAPI): List<String> {
        val nonEnglishTags: MutableList<String> = mutableListOf()
        val englishPattern = Regex("^[a-zA-Z0-9\\s]+$")

        // Check global tags
        openAPI.tags?.forEach { tag ->
            if (!tag.name.matches(englishPattern)) {
                nonEnglishTags.add("Глобальный тег \"${tag.name}\" содержит неанглийские символы.")
            }
        }

        // Check tags in operations
        openAPI.paths?.forEach { (path, pathItem) ->
            pathItem.readOperationsMap().forEach { (method, operation) ->
                operation.tags?.forEach { tag ->
                    if (!tag.matches(englishPattern)) {

                        nonEnglishTags.add("Тег \"$tag\" в операции \"$method $path\" содержит неанглийские символы.")
                    }
                }
            }
        }

        return nonEnglishTags
    }
}