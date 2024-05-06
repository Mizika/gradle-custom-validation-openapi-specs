package custom.validation.openapi.rules.methods.description

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem

/**
 * Класс для проверки, что для методов присутствует поле Description и не имеет значение null
 */
class DescriptionForMethodIsNotEmpty {
    fun checkDescriptionIsNotEmpty(openAPI: OpenAPI): List<String>? {
        val method: List<String> = ArrayList(openAPI!!.paths.keys)
        val descriptionEmpty: MutableList<String> = ArrayList()
        for (methodName: String in method) {
            for (operationMethod: PathItem.HttpMethod in openAPI!!.paths[methodName]!!.readOperationsMap().keys) {
                val operation = openAPI!!.paths[methodName]!!.readOperationsMap()[operationMethod]
                try {
                    if (operation!!.description == "null" || operation.description.length <= 2) {
                        descriptionEmpty.add(
                            "Для метода \"$operationMethod: $methodName\" пустое поле description"
                        )
                    }
                } catch (e: Exception) {
                    descriptionEmpty.add(
                        "Для метода \"$operationMethod: $methodName\" отсутствует поле description"
                    )
                }
            }
        }
        return descriptionEmpty
    }
}