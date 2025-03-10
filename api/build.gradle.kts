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
    implementation(projects.core)
    implementation(projects.exception)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.kotlinxSerialization)
    implementation(libs.bundles.springBootValidation)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.1")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(libs.logbackDiscordAppender)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.springBootActuator)
    implementation(libs.promethus)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    testImplementation(libs.bundles.testContainer)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
    testImplementation(libs.bundles.kotlinxSerialization)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}
