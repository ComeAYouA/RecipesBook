plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "lithium.kotlin.recipesbook.core.network"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp)

    implementation(project(":core:model"))
}