import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val apiKey: String = gradleLocalProperties(rootDir).getProperty("apiKey")

plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "lithium.kotlin.recipesbook.core.network"

    buildFeatures {
        buildConfig = true
    }

    buildTypes{
        getByName("debug"){
            buildConfigField("String", "apiKey", apiKey)
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp)

    implementation(project(":core:model"))
}