package custom.validation.openapi.rules.methods.errorcode

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что присутствуют обязательные коды ответа для всех методов [200, 400, 500]
 */
class BasicResponseCode {
    fun checkBasicResponseCode(openAPI: OpenAPI): List<String> {
        val missingCodes: MutableList<String> = mutableListOf()

        openAPI.paths.forEach { (path, pathItem) ->
            pathItem.readOperationsMap().forEach { (method, operation) ->
                if (operation.tags != null || operation.responses != null) {
                    val actualResponseCodes = operation.responses.keys.toList()
                    val responseCodeCategories = mapOf(
                        "20x" to { codes: List<String> -> codes.filter { it.startsWith("20") } },
                        "40x" to { codes: List<String> -> codes.filter { it.startsWith("40") } }
                        //TODO: подумать нужно ли включать
//                        "50x" to { codes: List<String> -> codes.filter { it.startsWith("50") } }
                    )
                    val missing = responseCodeCategories.filter { (_, filter) ->
                        filter(actualResponseCodes).isEmpty()
                    }.keys.toList()
                    if (missing.isNotEmpty()) {
                        missingCodes.add(
                            "Для метода \"$method: $path\" отсутствует обязательные коды ответа: ${missing.joinToString()}"
                        )
                    }
                }
            }
        }
        return missingCodes
    }
}