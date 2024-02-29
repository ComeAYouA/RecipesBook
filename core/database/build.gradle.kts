plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "lithium.kotlin.recipesbook.database"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(project(":core:model"))
}