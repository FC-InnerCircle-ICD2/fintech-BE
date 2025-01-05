plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

gradlePlugin {
    plugins {
        create("KotlinJvmConventionPlugin") {
            id = "KotlinJvmConventionPlugin"
            implementationClass = "KotlinJvmConventionPlugin"
        }
    }
}

dependencies {
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.kotlinterGradlePlugin)
}
