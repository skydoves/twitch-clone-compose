@file:Suppress("UnstableApiUsage")
pluginManagement {
  includeBuild("build-logic")
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven(url = "https://plugins.gradle.org/m2/")
  }
}
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://plugins.gradle.org/m2/")
  }
}

rootProject.name = "TwitchCloneCompose"
include(":app")
include(":core-designsystem")
include(":core-navigation")
include(":core-model")
include(":core-network")
include(":core-database")
include(":core-data")
include(":feature-channels")
include(":feature-messages")
include(":feature-livestream")
include(":baselineprofile")
