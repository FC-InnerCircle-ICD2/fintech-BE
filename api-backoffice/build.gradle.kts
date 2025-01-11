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
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.kotlinxSerialization)
    testImplementation(libs.bundles.kotlinTest)
    testImplementation(libs.bundles.kotlinTestSpring)
}
