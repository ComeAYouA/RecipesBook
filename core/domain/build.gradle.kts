plugins {
    id("recipes-book.android.library")
    id("recipes-book.android.hilt")
}

android {
    namespace = "lithium.kotlin.recipesbook.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
}