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
                compileSdk = 34

                defaultConfig {
                    minSdk = 24
                    lint.targetSdk = 34
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }
        }

    }
}