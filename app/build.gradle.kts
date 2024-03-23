plugins {
    id ("recipes-book.android.application")
    id ("recipes-book.android.application.compose")
    id ("recipes-book.android.hilt")
}

android {
    namespace = "lithium.kotlin.recipesbook"

    packaging {
        resources {
            excludes += "META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt{

}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.glide.compose)


    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)

    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project((":core:ui")))
    implementation(project((":feature:feed")))
}