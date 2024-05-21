package custom.validation.openapi.rules.methods.parameters

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.media.Schema

/**
 * Класс для проверки, что имя енама в параметрах запроса прописано в стиле аппер кейс и имеет нижнее подчеркивание где это необходимо
 */
class EnumNameInParameters {

    fun checkEnumNamesInParameters(openAPI: OpenAPI): List<String> {
        val checkEnumName = mutableListOf<String>()
        val camelCasePattern = Regex("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$")

        openAPI.paths.forEach { (path, pathItem) ->
            pathItem.readOperationsMap().forEach { (method, operation) ->
                operation.parameters?.forEach { parameter ->
                    parameter.schema?.enum?.forEach { enumValue ->
                        if (!enumValue.toString().matches(camelCasePattern)) {
                            checkEnumName.add(
                                "Некорректный формат ENUM \"$enumValue\" в параметре \"${parameter.name}\" метода \"$method $path\"!"
                            )
                        }
                    }
                }
            }
        }

        return checkEnumName
    }

}