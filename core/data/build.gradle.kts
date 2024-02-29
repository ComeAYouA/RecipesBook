plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.hilt")
}

android {
    namespace = "lithium.kotlin.recipesbook.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
}