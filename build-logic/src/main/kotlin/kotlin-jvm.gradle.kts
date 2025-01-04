import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jmailen.gradle.kotlinter.tasks.InstallPreCommitHookTask
import org.jmailen.gradle.kotlinter.tasks.InstallPrePushHookTask

plugins {
    kotlin("jvm")
    id("org.jmailen.kotlinter")
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

tasks.named("build").configure {
    if (!rootProject.extra.has("internal.install-git-hooks") &&
        !project.hasProperty("skipGitHooks")
    ) {
        rootProject.extra.set("internal.install-git-hooks", true)

        val preCommit: InstallPreCommitHookTask by rootProject.tasks.creating(
            InstallPreCommitHookTask::class
        ) {
            group = "build setup"
            description = "Installs Kotlinter Git pre-commit hook"
        }

        val prePush: InstallPrePushHookTask by rootProject.tasks.creating(
            InstallPrePushHookTask::class
        ) {
            group = "build setup"
            description = "Installs Kotlinter Git pre-push hook"
        }

        dependsOn(preCommit, prePush)
    }
}
