rootProject.name = "Stashbox"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("io.spring.dependency-management") version providers.gradleProperty("dependencyManagement").get()
        id("org.springframework.boot") version providers.gradleProperty("springframeworkBoot").get()
        id("io.gitlab.arturbosch.detekt") version providers.gradleProperty("detekt").get()
        id("org.jlleitschuh.gradle.ktlint") version providers.gradleProperty("ktlint").get()
    }
}