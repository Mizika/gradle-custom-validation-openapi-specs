package custom.validation.openapi.rules.methods.operationid

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import java.nio.file.Path

/**
 * Класс для проверки, что для методов в поле OperationId значение прописано в стиле camelCase
 */
class OperationIdCamelCase {
    fun checkOperationIdCamelCase(openAPI: OpenAPI): List<String>? {
        val method: List<String> = ArrayList(openAPI!!.paths.keys)
        val operationIdCamelCase: MutableList<String> = ArrayList()
        val camelCasePattern = "([a-z]+[A-Z]+\\w+)+"
        for (methodName: String in method) {
            for (operationMethod: PathItem.HttpMethod in openAPI!!.paths[methodName]!!.readOperationsMap().keys) {
                val operation = openAPI!!.paths[methodName]!!.readOperationsMap()[operationMethod]
                try {
                    if (operation!!.tags == null || operation.tags != null) {
                        if (operation.operationId != "null") {
                            if (!operation.operationId.matches(camelCasePattern.toRegex())) {
                                operationIdCamelCase.add(
                                    "Некорректный формат имени \"${operation.operationId}\" " +
                                            "для поля operationId в методе \"$operationMethod: $methodName\""
                                )
                            }
                        }
                    }
                } catch (ignored: Exception) {
                }
            }
        }
        return operationIdCamelCase
    }
}