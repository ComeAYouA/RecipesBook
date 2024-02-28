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
                compileSdk = 34

                defaultConfig {
                    minSdk = 24

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
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
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