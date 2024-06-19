package custom.validation.openapi.rules.methods.tags

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что в методах присутствуют только те теги, которые определены в секции tags спецификации OpenAPI
 */
class CheckOperationTagsValidity {

    fun checkOperationTagsValidity(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()
        val validTags = openAPI.tags?.map { it.name }?.toSet() ?: emptySet()

        openAPI.paths?.forEach { (path, pathItem) ->
            pathItem?.readOperationsMap()?.forEach { (httpMethod, operation) ->
                operation?.tags?.forEach { tagName ->
                    if (tagName !in validTags) {
                        issues.add("В операции ${httpMethod.name} $path используется тэг \"$tagName\" не из списка тэгов. " +
                                "Допустимые тэги: [${validTags.joinToString(", ")}]")
                    }
                }
            }
        }
        return issues
    }
}