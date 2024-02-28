plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.library.compose")
}

android {
    namespace = "lithium.kotlin.recipesbook.core.ui"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(project(":core:model"))
}