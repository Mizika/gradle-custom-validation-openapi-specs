package custom.validation.openapi.utils

import java.io.File
import java.nio.charset.StandardCharsets

/**
 * Класс с методом для сохранения в файл
 */
class SaveToFile {
    fun saveErrorsToFile(errors: MutableMap<String, MutableList<String>>, fileName: String) {
        val file = File(fileName)
        if (file.exists()) {
            file.delete()
        }
        file.parentFile.mkdirs()
        file.createNewFile()
        file.bufferedWriter(StandardCharsets.UTF_8).use { writer ->
            errors.forEach { (specName, errorsList) ->
                if (errorsList.isNotEmpty()) {
                    writer.write("############\n")
                    writer.write("$specName:\n")
                    writer.write("############\n")
                    errorsList.forEachIndexed { index, error ->
                        writer.write("${index + 1}. $error\n")
                    }
                }
            }
        }
    }
}