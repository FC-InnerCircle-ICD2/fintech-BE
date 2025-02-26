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
    implementation(projects.exception)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.kotlinxSerialization)
    implementation(libs.bundles.testContainer)
    implementation(libs.awsSecretsManagerConfig)
    implementation(libs.springBootDataJpa)
    implementation(libs.awsSecretsManagerConfig)
    implementation(libs.springBootDataRedis)
    implementation(libs.bundles.hypersistence)
    implementation(libs.guava)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.retry:spring-retry:2.0.3")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation(libs.h2)
    implementation("com.github.codemonstur:embedded-redis:1.4.3") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    runtimeOnly(libs.postgresql)
    compileOnly(libs.bundles.kotlinLogging)
    compileOnly(libs.bundles.kotlinxCoroutines)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
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
