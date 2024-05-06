package custom.validation.openapi.rules.methods.operationid

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import java.nio.file.Path

/**
 * Класс для проверки, что для методов присутствует поле OperationId и не имеет значение null
 */
class OperationIdIsNotEmpty {


    fun checkOperationIdIsNotEmpty(openAPI: OpenAPI): List<String>? {
        val method: List<String> = ArrayList(openAPI!!.paths.keys)
        val operationIdNotEmpty: MutableList<String> = ArrayList()
        for (methodName: String in method) {
            for (operationMethod: PathItem.HttpMethod in openAPI!!.paths[methodName]!!.readOperationsMap().keys) {
                val operation = openAPI!!.paths[methodName]!!.readOperationsMap()[operationMethod]
                try {
                    if (operation!!.operationId == null) {
                        operationIdNotEmpty.add(
                            "Для метода \"$operationMethod: $methodName\" пустое поле operationId"
                        )
                    }
                } catch (e: Exception) {
                    operationIdNotEmpty.add(
                        "Для метода \"$operationMethod: $methodName\" отсутствует поле operationId"
                    )
                }
            }
        }
        return operationIdNotEmpty
    }
}