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
    implementation(projects.coreBackoffice)
    implementation(projects.exception)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.kotlinxSerialization)
    implementation(libs.bundles.springBootValidation)
    implementation(libs.swagger)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
    testImplementation(libs.bundles.testContainer)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}
