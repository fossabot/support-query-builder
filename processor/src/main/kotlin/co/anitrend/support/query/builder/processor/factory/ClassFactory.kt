package co.anitrend.support.query.builder.processor.factory

import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import co.anitrend.support.query.builder.processor.model.Candidate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.util.Elements

internal class ClassFactory(
    private val processingEnvironment: ProcessingEnvironment,
    private val elements: Elements,
    private val logger: ILogger
) {

    private fun TypeSpec.Builder.construct(item: Candidate) : FileSpec {
        val typeSpec = build()
        logger.debug("Created type spec:")
        logger.debug("$typeSpec")
        return FileSpec.builder(
            item.packageName(elements),
            item.createFileName()
        ).addType(typeSpec).build()
    }

    private fun FileSpec.commit() {
        logger.debug("Committing construct using available options [${
            processingEnvironment.options.entries.joinToString { "${it.key}: ${it.value}" }
        }]")
        runCatching {
            val generatedDirectory = processingEnvironment.options[
                    GENERATED_OPTION_KEY
            ]
            writeTo(
                File(
                    requireNotNull(generatedDirectory) {
                        "processingEnvironment does not have options with key: $GENERATED_OPTION_KEY "
                    }
                )
            )
        }.onFailure {
            logger.error(it.message)
        }
    }

    private fun createTypeSpecBuilderWith(item: Candidate): TypeSpec.Builder {
        val builder = TypeSpec.objectBuilder(item.createFileName())
        item.getTable().writeToBuilder(builder)
        return builder
    }

    fun generateUsing(items: List<Candidate>) = items.forEach { elementItem ->
        logger.lineBreakWithSeperatorCharacter()
        logger.debug("Inspecting element `$elementItem` and preparing to generate object")
        val builder = runCatching { createTypeSpecBuilderWith(elementItem) }
            .onFailure { logger.error(it.message) }
            .getOrNull()
        builder?.construct(elementItem)?.commit()
    }

    private companion object {
        const val GENERATED_OPTION_KEY = "kapt.kotlin.generated"
    }
}