package custom.validation.openapi.rules.methods.parameters

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.parameters.Parameter
import java.nio.file.Path
/**
 * Класс для проверки, что для методов в поле parameters значение начинается с маленькой буквы
 */
class FormatNameOfParameters {
    fun checkFormatNameOfParameters(openAPI: OpenAPI): List<String>? {
        val method: List<String> = ArrayList(openAPI.paths.keys)
        val checkParamFormat: MutableList<String> = ArrayList()
        val camelCasePattern = "([a-z]+\\w+)+"
        for (methodName: String in method) {
            for (operationMethod: PathItem.HttpMethod in openAPI.paths[methodName]!!.readOperationsMap().keys) {
                val operation = openAPI.paths[methodName]!!.readOperationsMap()[operationMethod]
                try {
                    for (notHeaderParam: Parameter in operation!!.parameters) {
                        if (notHeaderParam.getIn() != "header") {
                            if (!notHeaderParam.name.matches(camelCasePattern.toRegex())) {
                                checkParamFormat.add(
                                    "Некорректный формат имени parameters для \"${notHeaderParam.name}\" в методе \"$operationMethod:$methodName\""
                                )
                            }
                        }
                    }
                } catch (ignored: Exception) {
                }
            }
        }
        return checkParamFormat
    }
}