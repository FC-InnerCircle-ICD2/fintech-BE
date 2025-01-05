package buildsrc.convention

import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jmailen.gradle.kotlinter.tasks.InstallPreCommitHookTask
import org.jmailen.gradle.kotlinter.tasks.InstallPrePushHookTask

plugins {
    kotlin("jvm")
    id("org.jmailen.kotlinter")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}

if (!rootProject.extra.has("buildsrc.internal.install-git-hooks")) {
    rootProject.extra.set("buildsrc.internal.install-git-hooks", true)

    val preCommit by project.rootProject.tasks.creating(InstallPreCommitHookTask::class) {
        group = "build setup"
        description = "Installs Kotlinter Git pre-commit hook"
    }

    val prePush by project.rootProject.tasks.creating(InstallPrePushHookTask::class) {
        group = "build setup"
        description = "Installs Kotlinter Git pre-push hook"
    }

    project.rootProject.tasks.getByName("prepareKotlinBuildScriptModel") {
        dependsOn(preCommit, prePush)
    }
}
