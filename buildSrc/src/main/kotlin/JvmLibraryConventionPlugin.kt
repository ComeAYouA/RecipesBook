import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }

            java().apply{
                sourceCompatibility = Versions.javaVersion
                targetCompatibility = Versions.javaVersion
            }
        }
    }

}