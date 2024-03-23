import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            androidApp().apply {
                compileSdk = Versions.compileSdk

                defaultConfig {
                    minSdk = Versions.minSdk
                    lint.targetSdk = Versions.targetSdk
                }

                compileOptions {
                    sourceCompatibility = Versions.javaVersion
                    targetCompatibility = Versions.javaVersion
                }
            }
        }

    }
}