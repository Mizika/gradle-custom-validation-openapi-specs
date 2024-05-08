package custom.validation.openapi.utils

import custom.validation.openapi.Extensions
import java.io.File

/**
 * Метод для получения конфигурационных правил
 */
class GetConfigRules {
    fun getConfigRules(extensions: Extensions, spec: File): MutableList<String> {
        val configRules: MutableList<String> = ArrayList()
        extensions.ignore[spec.nameWithoutExtension]?.let { config ->
            configRules.addAll(config)
        }
        return configRules
    }
}