plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    //kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
}

/* jacoco */
jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            limit {
                minimum = 0.60.toBigDecimal()
            }
        }
        rule {
            element = "METHOD"
            limit {
                minimum = 0.80.toBigDecimal()
            }
        }
    }
}



group = "com.inner.circle"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql")

    /* secret manager 를 위한 의존성 추가 */
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.2.0")
    implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:2.4.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
