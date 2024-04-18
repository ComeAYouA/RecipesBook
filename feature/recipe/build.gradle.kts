plugins {
    id("recipes-book.android.feature")
    id("recipes-book.android.hilt")
    id("recipes-book.android.library.compose")
}

android {
    namespace = "lithium.kotlin.recipesbook.feature.recipe"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.glide.compose)

    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
}