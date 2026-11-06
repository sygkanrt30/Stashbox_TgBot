rootProject.name = "Stashbox"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("io.spring.dependency-management") version providers.gradleProperty("dependencyManagement").get()
        id("org.springframework.boot") version providers.gradleProperty("springframeworkBoot").get()
        id("org.jlleitschuh.gradle.ktlint") version providers.gradleProperty("ktlint").get()
        id("dev.detekt") version providers.gradleProperty("detekt").get()
    }
}
