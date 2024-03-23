import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            androidLib().apply {
                compileSdk = Versions.compileSdk

                defaultConfig {
                    minSdk = Versions.minSdk

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = Versions.javaVersion
                    targetCompatibility = Versions.javaVersion
                }
            }

            dependencies {
                add(
                    "testImplementation",
                    versionCatalog.findLibrary("junit").get()
                )
                add(
                    "androidTestImplementation",
                    versionCatalog.findLibrary("androidx-test-ext-junit").get()
                )
                add(
                    "androidTestImplementation",
                    versionCatalog.findLibrary("espresso-core").get()
                )
            }
        }
    }

}