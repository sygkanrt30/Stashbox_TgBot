plugins {
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.spring") version "2.4.0"
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("dev.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "ru.yanin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val tgApi = providers.gradleProperty("tgApi").get()
val dotenv = providers.gradleProperty("dotenv").get()

dependencies {
    // telegram api
    implementation("org.telegram:telegrambots-springboot-webhook-starter:$tgApi") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-jackson")
    }
    implementation("org.telegram:telegrambots-client:$tgApi")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    developmentOnly("me.paulschwarz:springboot4-dotenv:$dotenv")

    implementation("org.springframework.boot:spring-boot-jackson2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property",
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn("ktlintFormat")
}

tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
    reports {
        html.required.set(true)
        sarif.required.set(true)
    }
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    jvmArgs(
        "-Dfile.encoding=UTF-8",
        "-Duser.timezone=UTC",
        "-XX:MaxRAMPercentage=70",
        "-XX:+HeapDumpOnOutOfMemoryError",
        "-XX:HeapDumpPath=${layout.buildDirectory.file("heapdump.hprof").get().asFile}",
    )
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    parallel = true
}

ktlint {
    android.set(false)
    ignoreFailures.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)

    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
    }
}
