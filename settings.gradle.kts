pluginManagement {
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
        google()
        mavenCentral()
    }
}

rootProject.name = "DobraShow"
include(":app")
include(":features:view-show")
include(":core")
include(":features:view-person")
include(":features:search")
include(":core:design")
include(":core:database")
include(":core:common")
include(":core:network")
include(":core:data")
include(":core:domain")
