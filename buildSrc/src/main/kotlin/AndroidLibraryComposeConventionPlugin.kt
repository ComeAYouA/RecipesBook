import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        with(target){

            androidLib().apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = versionCatalog
                        .findVersion("kotlinCompilerExtensionVersion").get().toString()
                }
            }

            dependencies {
                add("implementation", versionCatalog.findLibrary("compose-material3").get())
                add("implementation", versionCatalog.findLibrary("compose-ui-tooling").get())
                add("debugImplementation", versionCatalog.findLibrary("compose-ui-tooling-preview").get())
            }
        }
    }

}