import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("buildsrc.convention.kotlin-jvm")
    kotlin("plugin.noarg") version "1.9.10"
    alias(libs.plugins.kotlinPluginSerialization)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.kotlinPluginSpring)
    alias(libs.plugins.springDependencyManagement)
}

dependencies {
    libs.bundles.boms
        .get()
        .forEach { implementation(platform(it)) }
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.kotlinxSerialization)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.bundles.kotlinLogging)
    compileOnly(libs.bundles.kotlinxCoroutines)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
    testImplementation("com.h2database:h2")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
