package custom.validation.openapi.rules.methods.global

import io.swagger.v3.oas.models.OpenAPI

/**
 * Класс для проверки, что в секции info есть поле version, которое не пустое и соответствует паттерну SemVer (Semantic Versioning)
 */
class CheckInfoVersion {
    fun checkInfoVersion(openAPI: OpenAPI): List<String> {
        val issues: MutableList<String> = mutableListOf()

        val version = openAPI.info?.version

        if (version.isNullOrEmpty()) {
            issues.add("В секции info отсутствует или пустое поле version.")
        } else {
            val semVerPattern = Regex("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?(?:\\+([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?$")
            if (!version.matches(semVerPattern)) {
                issues.add("Поле version в секции info имеет некорректный формат (ожидался SemVer, пример 1.0.0).")
            }
        }
        return issues
    }

}