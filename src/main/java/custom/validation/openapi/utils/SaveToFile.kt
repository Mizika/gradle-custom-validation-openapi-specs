package custom.validation.openapi.utils

import java.io.File

/**
 * Класс с методом для сохранения в файл
 */
class SaveToFile {
    fun saveErrorsToFile(errors: MutableMap<String, MutableList<String>>, fileName: String) {
        val file = File(fileName)
        file.delete()
        file.parentFile.mkdirs()
        file.createNewFile()
        errors.forEach { (specName, errors) ->
            if (errors.isNotEmpty()) {
                file.appendText(
                    "############\n" +
                            "$specName:\n" +
                            "############\n"
                )
                errors.forEachIndexed { index, error ->
                    file.appendText("${index + 1}. $error\n")
                }
            }
        }
    }
}