package custom.validation.openapi

open class Extensions {
    /**
     * Параметр для указания пути до openapi контрактов
     */
    open var specPath: String = ""

    /**
     * Параметр для указания исключений правил для контракта
     */
    open var config: MutableMap<String, MutableMap<String, Boolean>> = mutableMapOf()

}