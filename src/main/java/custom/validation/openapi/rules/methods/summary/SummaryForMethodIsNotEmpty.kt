package custom.validation.openapi.rules.methods.summary

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem

/**
 * Класс для проверки, что для методов присутствует поле summary и не имеет значение null
 */
class SummaryForMethodIsNotEmpty {
    fun checkSummaryIsNotEmpty(openAPI: OpenAPI): List<String>? {
        val method: List<String> = ArrayList(openAPI!!.paths.keys)
        val descriptionEmpty: MutableList<String> = ArrayList()
        for (methodName: String in method) {
            for (operationMethod: PathItem.HttpMethod in openAPI!!.paths[methodName]!!.readOperationsMap().keys) {
                val operation = openAPI!!.paths[methodName]!!.readOperationsMap()[operationMethod]
                try {
                    if (operation!!.summary == "null" || operation.summary.length <= 2) {
                        descriptionEmpty.add(
                            "Для метода \"$operationMethod: $methodName\" пустое поле summary"
                        )
                    }
                } catch (e: Exception) {
                    descriptionEmpty.add(
                        "Для метода \"$operationMethod: $methodName\" отсутствует поле summary"
                    )
                }
            }
        }
        return descriptionEmpty
    }
}