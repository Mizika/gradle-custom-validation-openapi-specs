package custom.validation.openapi.utils

import custom.validation.openapi.Extensions
import java.io.File

/**
 * Метод для получения конфигурационных правил
 */
class GetConfigRules {
    fun getConfigRules(extensions: Extensions, spec: File): MutableMap<String, Boolean> {
        val configRules: MutableMap<String, Boolean> = mutableMapOf()
        extensions.config[spec.nameWithoutExtension]?.let { config ->
            configRules.putAll(config)
        }
        return configRules
    }
}