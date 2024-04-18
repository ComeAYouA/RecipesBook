buildscript {
    repositories{
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.hilt.agp)
    }
}



plugins{
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}