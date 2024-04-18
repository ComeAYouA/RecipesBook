pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RecipesBook"
include(":app")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":feature:feed")
include(":core:database")
include(":feature:recipe")
