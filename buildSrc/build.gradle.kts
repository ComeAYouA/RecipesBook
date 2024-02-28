import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
}

dependencies{
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle.build.tools)
    implementation(libs.javapoet)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}


gradlePlugin{
    plugins{
        register("androidApplicationConvention") {
            id = "recipes-book.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryComposeConvention") {
            id = "recipes-book.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHiltConvention") {
            id = "recipes-book.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("jvmLibraryConvention") {
            id = "recipes-book.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "recipes-book.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeatureConvention") {
            id = "recipes-book.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidApplicationComposeConvention") {
            id = "recipes-book.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
}