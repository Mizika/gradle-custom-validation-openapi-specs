package custom.validation.openapi.rules.methods.tags

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что в каждом методе  указан тэг
 */
class CheckOperationTags {

    fun checkOperationTags(openAPI: OpenAPI): List<String> {
        val missingTags: MutableList<String> = mutableListOf()

        openAPI.paths?.forEach { (path, pathItem) ->
            pathItem?.readOperationsMap()?.forEach { (httpMethod, operation) ->
                if (operation.tags.isNullOrEmpty()) {
                    missingTags.add("В методе ${httpMethod.name} $path не указан тэг.")
                }
            }
        }

        return missingTags
    }
}