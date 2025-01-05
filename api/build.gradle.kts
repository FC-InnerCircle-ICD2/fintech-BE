plugins {
    id("KotlinJvmConventionPlugin")
    alias(libs.plugins.springBoot)
    alias(libs.plugins.kotlinPluginSpring)
    alias(libs.plugins.springDependencyManagement)
}

dependencies {
    libs.bundles.boms
        .get()
        .forEach { implementation(platform(it)) }
    implementation(projects.core)
    implementation(libs.bundles.springBoot)
    testImplementation(libs.bundles.kotlinTestSpring)
}
