package custom.validation.openapi

import custom.validation.openapi.rules.methods.CombinedCheckMethods
import custom.validation.openapi.rules.models.CombinedCheckModels
import custom.validation.openapi.utils.FindSpecificationFiles
import custom.validation.openapi.utils.GetConfigRules
import io.swagger.parser.OpenAPIParser
import org.gradle.api.Plugin
import org.gradle.api.Project

class Plugin : Plugin<Project> {


    override fun apply(target: Project) {
        println(
            """
            Apply validation openapi specs plugin
        """.trimIndent()
        )

        val extensions: Extensions = target.extensions.create("validator", Extensions::class.java)

        target.afterEvaluate {
            val pathToProject = target.projectDir.absolutePath
            target.tasks.register("validator") { task ->
                task.doLast {
                    FindSpecificationFiles().findSpecs(pathToSpec = extensions.specPath, pathToProject = pathToProject)
                        .forEach { spec ->
                            val ignore: MutableList<String> = GetConfigRules()
                                .getConfigRules(extensions = extensions, spec = spec)

                            println("\u001b[0;33m################"
                                +"\nStart check: ${spec.name.toUpperCase()}"
                                +"\n################\u001b[0m"
                            )

                            val openAPI = OpenAPIParser().readLocation(spec.toString(), null, null).openAPI
                            val errorInSpec: MutableList<String> = ArrayList()

                            val checkMethods = CombinedCheckMethods().combinedCheckMethods(openAPI, ignore)
                            errorInSpec.addAll(checkMethods)

                            val checkModels = CombinedCheckModels().combinedCheckModels(openAPI, ignore)
                            errorInSpec.addAll(checkModels)

                            if (errorInSpec.isNotEmpty()) {
                                errorInSpec.forEachIndexed { index, error ->
                                    println("\u001b[0;31m${index + 1}. $error\u001b[0m")
                                }
                            } else {
                                println("\u001b[0;32mСпецификация в порядке, так держать!\u001b[0m")
                            }
                        }
                }
                task.group = "validation specs"
            }
        }
    }
}