import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.dagger.hilt.android")
                apply("kotlin-kapt")
            }

            dependencies{
                add(
                    "implementation",
                    versionCatalog.findLibrary("hilt-android").get()
                )
                add(
                    "kapt",
                    versionCatalog.findLibrary("hilt-compiler").get()
                )
            }

            kapt().apply{
                correctErrorTypes = true
            }
        }
    }

}