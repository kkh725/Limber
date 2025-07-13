pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        flatDir {
            dirs ("libs")
        }
    }
}

rootProject.name = "limber"
include(":app")
//include(":core")
include(":core:data")
//include(":mylibrary")
//include(":mylibrary:mylibrary")
//include(":build-logic")
//include(":build-logic:convention")
include(":feature")
include(":core:network")
include(":core:datastore")
//include(":feature:test")
include(":core:domain")
include(":feature:home")
include(":core:accessibility")
include(":core:ui")
include(":feature:block")
include(":feature:timer")
include(":feature:onboarding")
