plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}


kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.kotlinterGradlePlugin)
}
