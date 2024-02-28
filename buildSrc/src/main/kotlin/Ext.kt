import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

val Project.versionCatalog
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.androidApp(): BaseAppModuleExtension {
    return extensions.getByType(BaseAppModuleExtension::class.java)
}

fun Project.androidLib(): LibraryExtension {
    return extensions.getByType(LibraryExtension::class.java)
}

fun Project.kapt(): KaptExtension {
    return extensions.getByType(KaptExtension::class.java)
}

fun Project.java(): JavaPluginExtension{
    return extensions.getByType(JavaPluginExtension::class.java)
}

