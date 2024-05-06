package custom.validation.openapi

import custom.validation.openapi.utils.FindSpecificationFiles
import org.gradle.api.Plugin
import org.gradle.api.Project

class Plugin : Plugin<Project> {


    override fun apply(target: Project) {
        println(
            """
            ############
            Apply validation openapi specs plugin
            ############
        """.trimIndent()
        )

        val extensions: Extensions = target.extensions.create("validator", Extensions::class.java)

        target.afterEvaluate {
            val pathToProject = target.projectDir.absolutePath
            FindSpecificationFiles().findSpecs(pathToSpec = extensions.specPath, pathToProject = pathToProject)
                .forEach { spec ->
                    println(spec.name)
                }
        }
    }
}