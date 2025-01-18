import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("buildsrc.convention.kotlin-jvm")
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
    implementation(projects.infraBackoffice)
    implementation(projects.exception)
    compileOnly(libs.bundles.kotlinLogging)
    compileOnly(libs.bundles.kotlinxCoroutines)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
