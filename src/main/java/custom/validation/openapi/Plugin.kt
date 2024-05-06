package custom.validation.openapi

import custom.validation.openapi.rules.methods.description.DescriptionForMethodIsNotEmpty
import custom.validation.openapi.rules.methods.errorcode.BasicResponseCode
import custom.validation.openapi.rules.methods.operationid.OperationIdCamelCase
import custom.validation.openapi.rules.methods.operationid.OperationIdIsNotEmpty
import custom.validation.openapi.rules.methods.summary.SummaryForMethodIsNotEmpty
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
                            println("\u001b[0;33m################"
                                +"\nStart check: ${spec.name.toUpperCase()}"
                                +"\n################\u001b[0m"
                            )
                            val openAPI = OpenAPIParser().readLocation(spec.toString(), null, null).openAPI
                            val errorInSpec: MutableList<String> = ArrayList()

                            val operationIdCamelCase = OperationIdCamelCase().checkOperationIdCamelCase(openAPI)
                            errorInSpec.addAll(operationIdCamelCase!!)

                            val operationIdNotEmpty = OperationIdIsNotEmpty().checkOperationIdIsNotEmpty(openAPI)
                            errorInSpec.addAll(operationIdNotEmpty!!)

                            val descriptionNotEmpty = DescriptionForMethodIsNotEmpty().checkDescriptionIsNotEmpty(openAPI)
                            errorInSpec.addAll(descriptionNotEmpty!!)

                            val summaryNotEmpty = SummaryForMethodIsNotEmpty().checkSummaryIsNotEmpty(openAPI)
                            errorInSpec.addAll(summaryNotEmpty!!)

                            val basicResponseCode = BasicResponseCode().checkBasicResponseCode(openAPI)
                            errorInSpec.addAll(basicResponseCode!!)

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