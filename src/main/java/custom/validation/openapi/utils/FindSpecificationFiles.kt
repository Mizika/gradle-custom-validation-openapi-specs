package custom.validation.openapi.utils

import java.io.File

open class FindSpecificationFiles {

    fun findSpecs(pathToSpec: String, pathToProject: String): MutableList<File> {
        val nameSpecs: MutableList<File> = mutableListOf()
        File("$pathToProject/$pathToSpec").walkTopDown().maxDepth(2).forEach {
            if (it.name.contains(Regex("^.*.yaml$|^.*.yml$"))) {
                nameSpecs.add(it)
            }
        }
        return nameSpecs
    }

}