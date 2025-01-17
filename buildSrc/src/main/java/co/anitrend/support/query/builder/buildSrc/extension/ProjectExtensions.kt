package co.anitrend.support.query.builder.buildSrc.extension

import co.anitrend.support.query.builder.buildSrc.module.Modules
import com.android.build.gradle.*
import com.android.build.gradle.api.AndroidBasePlugin
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.internal.plugins.DefaultArtifactPublicationSet
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.reporting.ReportingExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.testing.internal.KotlinTestsRegistry


fun Project.isAppModule() = name == Modules.App.Main.id
fun Project.isAnnotationModule() = name == Modules.Common.Annotation.id
fun Project.isCoreModule() = name == Modules.Common.Core.id
fun Project.isProcessorModule() = name == Modules.Processor.Core.id
fun Project.isProcessorRoomModule() = name == Modules.Processor.Room.id

fun Project.isKotlinLibraryGroup() = name.startsWith(Modules.processorModulePattern) || isAnnotationModule()
fun Project.matchesProcessorModule() = name.startsWith(Modules.processorModulePattern)

internal fun Project.baseExtension() =
    extensions.getByType<BaseExtension>()

internal fun Project.kotlinJvmExtension() =
    extensions.getByType<KotlinJvmProjectExtension>()

internal fun Project.baseAppExtension() =
    extensions.getByType<BaseAppModuleExtension>()

internal fun Project.libraryExtension() =
    extensions.getByType<LibraryExtension>()

internal fun Project.dynamicFeatureExtension() =
    extensions.getByType<BaseAppModuleExtension>()

internal fun Project.extraPropertiesExtension() =
    extensions.getByType<ExtraPropertiesExtension>()

internal fun Project.defaultArtifactPublicationSet() =
    extensions.getByType<DefaultArtifactPublicationSet>()

internal fun Project.reportingExtension() =
    extensions.getByType<ReportingExtension>()

internal fun Project.sourceSetContainer() =
    extensions.getByType<SourceSetContainer>()

internal fun Project.javaPluginExtension() =
    extensions.getByType<JavaPluginExtension>()

internal fun Project.variantOutput() =
    extensions.getByType<BaseVariantOutput>()

internal fun Project.kotlinAndroidProjectExtension() =
    extensions.getByType<KotlinAndroidProjectExtension>()

internal fun Project.kotlinTestsRegistry() =
    extensions.getByType<KotlinTestsRegistry>()

internal fun Project.androidExtensionsExtension() =
    extensions.getByType<AndroidExtensionsExtension>()

internal fun Project.publishingExtension() =
    extensions.getByType<PublishingExtension>()

internal fun Project.defaultPublicationSet() =
    extensions.getByType<DefaultArtifactPublicationSet>()

internal fun Project.containsAndroidPlugin(): Boolean {
    return project.plugins.toList().any { plugin ->
        plugin is AndroidBasePlugin
    }
}

internal fun Project.containsLibraryPlugin(): Boolean {
    return project.plugins.toList().any { plugin ->
        plugin is LibraryPlugin
    }
}

internal fun Project.containsDynamicFeaturePlugin(): Boolean {
    return project.plugins.toList().any { plugin ->
        plugin is DynamicFeaturePlugin
    }
}

internal fun Project.containsTestPlugin(): Boolean {
    return project.plugins.toList().any { plugin ->
        plugin is TestPlugin
    }
}
