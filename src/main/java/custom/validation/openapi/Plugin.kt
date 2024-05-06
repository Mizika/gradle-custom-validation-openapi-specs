package custom.validation.openapi

import custom.validation.openapi.rules.methods.operationid.OperationIdCamelCase
import custom.validation.openapi.rules.methods.operationid.OperationIdIsNotEmpty
import custom.validation.openapi.utils.FindSpecificationFiles
import io.swagger.parser.OpenAPIParser
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
            target.tasks.register("validator") { task ->
                task.doLast {
                    FindSpecificationFiles().findSpecs(pathToSpec = extensions.specPath, pathToProject = pathToProject)
                        .forEach { spec ->
                            println(
                                """
                        ################
                        Start check: ${spec.name.toUpperCase()}
                        ################
                    """.trimIndent()
                            )
                            val openAPI = OpenAPIParser().readLocation(spec.toString(), null, null).openAPI
                            val errorInSpec: MutableList<String> = ArrayList()

                            val operationIdCamelCase = OperationIdCamelCase().checkOperationIdCamelCase(openAPI)
                            errorInSpec.addAll(operationIdCamelCase!!)

                            val operationIdNotEmpty = OperationIdIsNotEmpty().checkOperationIdIsNotEmpty(openAPI)
                            errorInSpec.addAll(operationIdNotEmpty!!)

                            if (errorInSpec.isNotEmpty()) {
                                errorInSpec.forEachIndexed { index, error ->
                                    println("\u001b[0;31m${index + 1}. $error")
                                }
                            } else {
                                println("\u001b[0;32mСпецификация в порядке, так держать!")
                            }
                        }
                }
                task.group = "validation specs"
            }
        }
    }
}